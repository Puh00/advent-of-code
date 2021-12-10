#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <stack>
#include <queue>
#include <map>
#include <algorithm>

using std::endl;
using std::map;
using std::pair;
using std::queue;
using std::stack;
using std::string;
using std::vector;

#define ll long long

void solve(vector<queue<char>> input)
{
  // map what each chunk expects and its value
  const map<char, pair<char, int>> error_scores{
      {')', {'(', 3}},
      {']', {'[', 57}},
      {'}', {'{', 1197}},
      {'>', {'<', 25137}},
  };

  const map<char, int> auto_scores{
      {'(', 1},
      {'[', 2},
      {'{', 3},
      {'<', 4},
  };

  ll total_error_score = 0;
  vector<ll> autos;

  for (auto &line : input)
  {
    bool is_corrupted = false;
    stack<char> chunks;
    // manually push first char
    chunks.push(line.front());
    line.pop();

    while (!line.empty())
    {
      if (!error_scores.count(line.front()) || chunks.empty())
      { // if opening chunk or stack is empty then push to stack
        chunks.push(line.front());
        line.pop();
        continue;
      }

      if (error_scores.at(line.front()).first == chunks.top())
      { // legal chunk, pop from both stack and queue
        chunks.pop();
        line.pop();
      }
      else
      { // illegal chunk
        total_error_score += error_scores.at(line.front()).second;
        is_corrupted = true;
        break;
      }
    }

    // part b
    if (is_corrupted)
      // don't calculate the auto score for corrupetd lines
      continue;

    ll total_auto_score = 0;
    while (!chunks.empty())
    {
      total_auto_score *= 5;
      total_auto_score += auto_scores.at(chunks.top());
      chunks.pop();
    }
    autos.push_back(total_auto_score);
  }

  std::cout << "Error score > " << total_error_score << endl;

  std::sort(autos.begin(), autos.end());
  std::cout << "Auto score > " << autos[autos.size() / 2] << endl;
}

int main()
{
  std::ifstream file("input.txt");
  vector<queue<char>> input;
  for (string line; std::getline(file, line);)
  {
    queue<char> tmp;
    for (char c : line)
      tmp.push(c);
    input.emplace_back(tmp);
  }

  solve(input);

  return 0;
}