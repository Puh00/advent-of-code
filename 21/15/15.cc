#include <fstream>
#include <functional>
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

// up, right, south, left
static const std::vector<int> y_dir = {-1, 0, 1, 0};
static const std::vector<int> x_dir = {0, 1, 0, -1};

// returns -1 if goal was not found, else the path cost from start to goal
int uniform_cost_search(
    grid const& g, coord const goal,
    std::function<std::vector<std::pair<coord, int>>(grid, coord)> const&
        neighbors) {
  // visited nodes
  std::set<coord> visited;
  // compare the path cost of nodes
  const auto cmp = [](node const& lhs, node const& rhs) {
    return lhs.path_cost > rhs.path_cost;
  };
  // min-heap
  std::priority_queue<node, std::vector<node>, decltype(cmp)> pq(cmp);
  // start from top left corner
  pq.push(node({0, 0}, 0));

  while (!pq.empty()) {
    auto const entry = pq.top();  // & will cause memory issues
    pq.pop();

    // return path cost when goal has been found
    if (entry.c == goal) return entry.path_cost;

    if (!visited.count(entry.c)) {
      visited.insert(entry.c);
      for (auto const& c : neighbors(g, entry.c))
        pq.push(node(c.first, entry.path_cost + c.second));
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

  static const auto neigbors_a =
      [](grid const& g, coord const& c) -> std::vector<std::pair<coord, int>> {
    std::vector<std::pair<coord, int>> neighbors;
    for (int i = 0; i < x_dir.size(); i++) {
      const int y_coord = c.first + y_dir[i];
      const int x_coord = c.second + x_dir[i];
      if (y_coord >= 0 && y_coord < g.size() && x_coord >= 0 &&
          x_coord < g.front().size())
        neighbors.push_back({coord(y_coord, x_coord), g[y_coord][x_coord]});
    }
    return neighbors;
  };

  static const auto neigbors_b =
      [](grid const& g, coord const& c) -> std::vector<std::pair<coord, int>> {
    std::vector<std::pair<coord, int>> neighbors;
    for (int i = 0; i < x_dir.size(); i++) {
      int const y_coord = c.first + y_dir[i];
      int const x_coord = c.second + x_dir[i];

      // only check coordinates that are within the new extended dimension
      if (y_coord >= 0 && y_coord < g.size() * 5 && x_coord >= 0 &&
          x_coord < g.front().size() * 5) {
        // the additional number to add depending on the which tile the
        // coord is at
        int dim = y_coord / (g.size() - 1);       // y
        dim += x_coord / (g.front().size() - 1);  // x

        // 10 wraps back to 1 instead of 0
        const int actual_value =
            g[y_coord % g.size()][x_coord % g.front().size()];
        const int risk_level = ((actual_value - 1) + dim) % 9 + 1;

        neighbors.push_back({coord(y_coord, x_coord), risk_level});
      }
    }
    return neighbors;
  };

  const coord goal_a{g.size() - 1, g.front().size() - 1};
  std::cout << "part a > " << uniform_cost_search(g, goal_a, neigbors_a)
            << std::endl;

  const coord goal_b{g.size() * 5 - 1, g.front().size() * 5 - 1};
  std::cout << "part b > " << uniform_cost_search(g, goal_b, neigbors_b)
            << std::endl;

  return 0;
}