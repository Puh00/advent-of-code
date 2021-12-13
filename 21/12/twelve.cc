#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <map>
#include <deque>
#include <algorithm>
#include <cctype>
#include <functional>
#include <unordered_set>

using std::deque;
using std::endl;
using std::map;
using std::string;
using std::vector;

using graph = map<string, vector<string>>;

vector<string> split(const string s, const char delimiter)
{
  std::istringstream ss(s);
  vector<string> tokens;
  for (string token; std::getline(ss, token, delimiter);)
    if (!token.empty())
      tokens.push_back(token);
  return tokens;
}

void dfs(graph &g, deque<string> &visited, vector<vector<string>> &paths,
         string node, std::function<bool(deque<string>, string)> lambda)
{
  visited.push_back(node);
  if (node == "end")
  {
    paths.push_back(vector<string>(visited.begin(), visited.end()));
    visited.pop_back();
    return;
  }

  for (string n : g.at(node))
  {
    bool is_lowercase = std::all_of(n.begin(), n.end(), [](char c)
                                    { return islower(c); });
    bool is_visited = lambda(visited, n);

    if (!(is_lowercase && is_visited))
      dfs(g, visited, paths, n, lambda);
  }

  visited.pop_back();
}

void solve(graph &g)
{
  deque<string> visited;
  vector<vector<string>> paths;

  // lambda expressions for the different cases
  // check if node has been visited before
  const auto part_a = [](const deque<string> &visited, string node) -> bool
  {
    return std::any_of(visited.begin(), visited.end(), [node](string s)
                       { return s == node; });
  };
  const auto part_b = [](const deque<string> &visited, string node) -> bool
  {
    // only visit start and end once
    if (node == "start" || node == "end")
      return std::any_of(visited.begin(), visited.end(), [node](string s)
                         { return s == node; });

    // frequency table
    map<int, std::unordered_set<string>> freq;
    for (string s : visited)
    {
      // skip upper case caves
      if (std::any_of(s.begin(), s.end(), [](char c)
                      { return !islower(c); }))
        continue;

      if (freq[0].count(s))
      {
        freq[1].insert(s);
        freq[0].erase(s);
      }
      else
        freq[0].insert(s);
    }
    return !freq[1].empty() && (freq[0].count(node) || freq[1].count(node));
  };

  // part a
  dfs(g, visited, paths, "start", part_a);
  std::cout << paths.size() << endl;

  // part b
  visited.clear();
  paths.clear();
  dfs(g, visited, paths, "start", part_b);
  std::cout << paths.size() << endl;
}

int main()
{
  std::ifstream file("input.txt");
  graph adjacency_list;
  for (string line; std::getline(file, line);)
  {
    const auto edge = split(line, '-');
    adjacency_list[edge.front()].push_back(edge.back());
    adjacency_list[edge.back()].push_back(edge.front());
  }

  solve(adjacency_list);

  return 0;
}