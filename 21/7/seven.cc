#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <algorithm>
#include <chrono>

using std::endl;
using std::string;
using std::vector;

vector<int> split_by_delimiter(const string s, const char delimiter)
{
  std::istringstream ss(s);
  vector<int> tokens;
  string token;

  while (std::getline(ss, token, delimiter))
    if (!token.empty())
      tokens.push_back(std::stoi(token));
  return tokens;
}

void solve(vector<string> input, const bool part_b = false)
{
  vector<int> positions = split_by_delimiter(input.front(), ',');
  int max_x = *max_element(positions.begin(), positions.end());
  bool first_iter = true;
  int least_fuel_cost = -1;

  // formula for sum of first 'n' integers
  const auto geom_sum = [](int n)
  { return n * (n + 1) / 2; };

  for (int i = 0; i <= max_x; i++)
  {
    int iter_cost = 0;
    for (const int x : positions)
    {
      const int distance = abs(x - i);
      if (!part_b)
        iter_cost += distance;
      else
        iter_cost += geom_sum(distance);
    }

    least_fuel_cost = least_fuel_cost == -1 ? iter_cost : iter_cost < least_fuel_cost ? iter_cost
                                                                                      : least_fuel_cost;
  }
  std::cout << "Fuel cost > " << least_fuel_cost << endl;
}

int main()
{
  std::ifstream file("input.txt");
  vector<string> input;
  string line;
  for (string line; std::getline(file, line);)
    input.push_back(line);

  // part a
  solve(input);

  // part b
  auto start = std::chrono::high_resolution_clock::now();
  solve(input, true);
  auto stop = std::chrono::high_resolution_clock::now();
  auto duration = std::chrono::duration_cast<std::chrono::microseconds>(stop - start);
  std::cout << duration.count() << " ms" << endl;

  return 0;
}
