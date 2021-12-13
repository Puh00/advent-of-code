#include <fstream>
#include <iostream>
#include <regex>
#include <set>
#include <string>
#include <vector>

using std::set;
using std::string;
using std::vector;

using coord = std::pair<int, int>;

void fold(set<coord>& coords, coord const& fold_coord) {
  set<coord> to_be_erased;
  for (auto const& c : coords) {
    if (c.first < fold_coord.first && c.second < fold_coord.second) continue;
    // the new coordinate after fold
    coord folded{
        c.first > fold_coord.first ? (2 * fold_coord.first - c.first) : c.first,
        c.second > fold_coord.second ? (2 * fold_coord.second - c.second)
                                     : c.second};
    coords.insert(folded);
    to_be_erased.insert(c);
  }
  // remove old coordinates
  for (auto const& c : to_be_erased) coords.erase(c);
}

void print_code(set<coord> const& coords) {
  // get max width and height
  int max_x = 0, max_y = 0;
  for (auto const& c : coords) {
    if (c.first > max_x) max_x = c.first;
    if (c.second > max_y) max_y = c.second;
  }
  vector<vector<char>> matrix(max_y + 1, vector<char>(max_x + 1, '.'));
  // mark dots
  for (auto const& c : coords) matrix[c.second][c.first] = '#';

  // print matrix
  for (auto const& row : matrix) {
    for (auto const c : row) std::cout << c << ' ';
    std::cout << std::endl;
  }
  std::cout << std::endl;
}

int main() {
  static std::regex fold_x{"fold along x=(\\d+)"};
  static std::regex fold_y{"fold along y=(\\d+)"};

  std::ifstream file("input.txt");
  string line;
  set<coord> coords;
  while (std::getline(file, line) && !line.empty()) {
    size_t end = 0;
    auto x{std::stoi(line, &end)};
    auto y{std::stoi(line.substr(end + 1))};
    coords.insert({x, y});
  }

  int count = 0;
  while (std::getline(file, line)) {
    count++;
    std::smatch m;
    coord fold_coord{INT_MAX, INT_MAX};
    if (std::regex_search(line, m, fold_x))
      fold_coord.first = std::stoi(m.str(1));
    else if (std::regex_search(line, m, fold_y))
      fold_coord.second = std::stoi(m.str(1));
    fold(coords, fold_coord);

    std::cout << "After " << count << " folds, there are " << coords.size()
              << " dots" << std::endl;
  }
  print_code(coords);

  return 0;
}