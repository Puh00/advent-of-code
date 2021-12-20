#include <fstream>
#include <iostream>
#include <queue>  // std::priority_queue
#include <set>
#include <vector>

using grid = std::vector<std::vector<int>>;
using coord = std::pair<int, int>;  // y, x

struct node {
  coord c;
  int path_cost;
  node(coord _c, int _path_cost) : c(_c), path_cost(_path_cost) {}
};

std::vector<coord> neighbors(const grid& g, coord const& c) {
  // up, right, south, left
  const std::vector<int> y_dir = {-1, 0, 1, 0};
  const std::vector<int> x_dir = {0, 1, 0, -1};
  std::vector<coord> neighbors;

  for (int i = 0; i < x_dir.size(); i++) {
    const int y_coord = c.first + y_dir[i];
    const int x_coord = c.second + x_dir[i];
    if (y_coord >= 0 && y_coord < g.size() && x_coord >= 0 &&
        x_coord < g.front().size())
      neighbors.push_back(coord(y_coord, x_coord));
  }
  return neighbors;
}

// returns -1 if goal was not found, else the path cost from start to goal
int uniform_cost_search(grid const& g) {
  static coord goal{g.size() - 1, g.front().size() - 1};
  // visited nodes
  std::set<coord> visited;
  // compare the path cost of nodes
  auto cmp = [](node const& lhs, node const& rhs) {
    return lhs.path_cost > rhs.path_cost;
  };
  std::priority_queue<node, std::vector<node>, decltype(cmp)> pq(cmp);
  // add starting node
  pq.push(node({0, 0}, 0));

  while (!pq.empty()) {
    auto const entry = pq.top();  // & will cause memory issues
    pq.pop();

    // return path cost when goal has been found
    if (entry.c == goal) return entry.path_cost;

    if (!visited.count(entry.c)) {
      visited.insert(entry.c);

      for (auto const& c : neighbors(g, entry.c)) {
        int cost_to_next = entry.path_cost + g[c.first][c.second];
        pq.push(node({c.first, c.second}, cost_to_next));
      }
    }
  }
  return -1;
}

int main() {
  std::ifstream file("input.txt");
  grid g;
  for (std::string line; std::getline(file, line);) {
    std::vector<int> tmp;
    for (auto c : line) tmp.push_back(c - '0');
    g.push_back(tmp);
  }

  std::cout << "Cost of the path with lowest risk > " << uniform_cost_search(g)
            << std::endl;

  return 0;
}