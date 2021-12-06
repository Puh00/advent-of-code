#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <map>
#include <numeric>

using std::endl;
using std::map;
using std::string;
using std::vector;

vector<int> split_by_delimiter(const string s, const char delimiter)
{
  std::istringstream ss(s);
  vector<int> tokens;
  string token;

  while (std::getline(ss, token, delimiter))
  {
    if (!token.empty())
      tokens.push_back(std::stoi(token));
  }
  return tokens;
}

void solve(vector<string> input, const int days)
{
  vector<int> ages = split_by_delimiter(input.front(), ',');

  map<int, long long> m;
  for (int age : ages)
    m[age] += 1;

  for (int i = 0; i < days; i++)
  {
    auto zeros = m[0];
    for (int i = 0; i < 8; i++)
      m[i] = m[i + 1];
    m[8] = 0;
    m[6] += zeros;
    m[8] = zeros;
  }

  long long no_fishes = std::accumulate(m.begin(), m.end(), 0LL,
                                        [](long long l, const std::pair<const int, long long> &rhs)
                                        { return l + rhs.second; });
  std::cout << "Number of fishes after " << days << " days > " << no_fishes << endl;
}

int main()
{
  std::ifstream file("input.txt");
  vector<string> input;
  string line;
  while (std::getline(file, line))
    input.push_back(line);

  solve(input, 80);
  solve(input, 256);

  return 0;
}
