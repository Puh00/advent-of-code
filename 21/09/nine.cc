#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <algorithm>
#include <queue>
#include <map>

using std::endl;
using std::map;
using std::pair;
using std::queue;
using std::string;
using std::vector;

using grid = vector<vector<int>>;

struct coord
{
  int y;
  int x;
  coord(int _y, int _x)
  {
    y = _y;
    x = _x;
  }
  bool operator<(const coord &other) const
  {
    return std::pair<int, int>(y, x) < std::pair<int, int>(other.y, other.x);
  }
};

vector<pair<coord, int>> neighbors(const grid &g, int y, int x)
{
  // up, right, south, left
  const vector<int> y_dir = {-1, 0, 1, 0};
  const vector<int> x_dir = {0, 1, 0, -1};
  vector<pair<coord, int>> neighbors;

  for (int i = 0; i < x_dir.size(); i++)
  {
    const int y_cord = y + y_dir[i];
    const int x_cord = x + x_dir[i];
    if (y_cord >= 0 && y_cord < g.size() && x_cord >= 0 && x_cord < g.front().size())
      neighbors.push_back({coord(y_cord, x_cord), g[y_cord][x_cord]});
  }
  return neighbors;
}

// simple BFS that returns the total distance traveled
int size_of_basin(const grid &g, coord c)
{
  int distance = 0;
  map<coord, bool> visited;
  queue<coord> q;
  q.push(c);

  while (!q.empty())
  {
    const auto curr = q.front();
    q.pop();

    if (!visited[curr] && g[curr.y][curr.x] != 9)
    {
      distance++;
      visited[curr] = true;
      for (auto const &p : neighbors(g, curr.y, curr.x))
        q.push(p.first);
    }
  }
  return distance;
}

void solve(grid &g)
{
  vector<pair<coord, int>> low_points;

  for (int y = 0; y < g.size(); y++)
    for (int x = 0; x < g.front().size(); x++)
    {
      const int val = g[y][x];
      const auto ns = neighbors(g, y, x);
      // check if val is strictly lower than all neigbors
      if (std::all_of(ns.begin(), ns.end(), [val](const auto p)
                      { return val < p.second; }))
        low_points.push_back({coord(y, x), g[y][x]});
    }

  int risk_level = 0;
  for (auto p : low_points)
    risk_level += p.second + 1;
  std::cout << "Sum > " << risk_level << endl;

  // part b
  vector<int> basin_sizes;
  for (auto const &p : low_points)
    basin_sizes.push_back(size_of_basin(g, p.first));
  std::sort(basin_sizes.begin(), basin_sizes.end());

  int product = 1;
  for (int i = 0; i < 3; i++)
  {
    product *= basin_sizes.back();
    basin_sizes.pop_back();
  }
  std::cout << "Product > " << product << endl;
}

int main()
{
  std::ifstream file("input.txt");
  grid input;
  for (string line; std::getline(file, line);)
  {
    vector<int> tmp;
    for (char c : line)
      tmp.emplace_back(c - '0');
    input.emplace_back(tmp);
  }

  solve(input);

  return 0;
}
