#include <iostream>
#include <fstream>
#include <vector>
#include <set>

using std::endl;
using std::set;
using std::string;
using std::vector;

using grid = vector<vector<int>>;

struct coord
{
  int y;
  int x;
  coord(int y, int x)
  {
    this->y = y;
    this->x = x;
  }
  bool operator<(const coord &rhs) const
  { // lexographic comparison
    return y < rhs.y || (!(rhs.y < y) && x < rhs.x);
  }
};

void try_flash(grid &g, set<coord> &has_flashed, const int y, const int x, int &no_flashes)
{
  if (g[y][x] <= 9 || has_flashed.count({y, x}))
    return;

  has_flashed.insert({y, x});
  no_flashes++;

  for (int r = -1; r <= 1; r++)
    for (int c = -1; c <= 1; c++)
    {
      if (r == 0 && c == 0)
        continue;
      const int y_coord = r + y;
      const int x_coord = c + x;
      if (y_coord >= 0 && y_coord < g.size() && x_coord >= 0 && x_coord < g.front().size())
      {
        g[y_coord][x_coord]++;
        try_flash(g, has_flashed, y_coord, x_coord, no_flashes);
      }
    }
}

bool synchronized(const grid &g)
{
  for (auto const &row : g)
    for (auto const &c : row)
      if (c != 0)
        return false;
  return true;
}

void solve(grid g, int part_a = 100)
{
  int no_flashes = 0;
  int synchronized_at = -1;

  for (int i = 0; i < part_a; i++)
  {
    set<coord> has_flashed;
    // inc by one
    for (auto &row : g)
      for (auto &c : row)
        c++;

    //  flash octopuses
    for (int y = 0; y < g.size(); y++)
      for (int x = 0; x < g.front().size(); x++)
        try_flash(g, has_flashed, y, x, no_flashes);

    // reset flashed octopuses to 0
    for (auto &c : has_flashed)
      g[c.y][c.x] = 0;

    // skip if part a
    if (part_a == 100)
      continue;

    if (synchronized(g))
    {
      synchronized_at = i;
      break;
    }
  }
  std::cout << "No. flashes > " << no_flashes << endl;
  std::cout << "Synchronized at step > " << (synchronized_at + 1) << endl;
}

int main()
{
  std::ifstream file("input.txt");
  grid input;
  for (string line; std::getline(file, line);)
  {
    vector<int> tmp;
    for (char c : line)
      tmp.push_back(c - '0');
    input.push_back(tmp);
  }

  solve(input);
  solve(input, INT_MAX);

  return 0;
}