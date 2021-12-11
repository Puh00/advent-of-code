#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <map>

using std::endl;
using std::map;
using std::pair;
using std::string;
using std::vector;

using coord = pair<int, int>;

vector<string> split_by_delimiter(const string s, const char delimiter)
{
  std::istringstream ss(s);
  vector<string> tokens;
  string token;

  while (std::getline(ss, token, delimiter))
  {
    if (!token.empty())
      tokens.push_back(token);
  }
  return tokens;
}

coord to_coord(const vector<string> tokens)
{
  return std::make_pair(std::stoi(tokens.front()), std::stoi(tokens.back()));
}

pair<coord, coord> parse_line(const string s)
{
  const vector<string> tokens = split_by_delimiter(s, ' ');
  const coord from_coord = to_coord(split_by_delimiter(tokens.front(), ','));
  const coord toward_coord = to_coord(split_by_delimiter(tokens.back(), ','));
  return std::make_pair(from_coord, toward_coord);
}

vector<pair<coord, coord>> parse_data(const vector<string> &input)
{
  vector<pair<coord, coord>> lines;
  for (string s : input)
    lines.push_back(parse_line(s));
  return lines;
}

bool horizontally_aligned(const pair<coord, coord> line)
{
  return abs(line.first.first - line.second.first) == 0;
}

bool vertically_aligned(const pair<coord, coord> line)
{
  return abs(line.first.second - line.second.second) == 0;
}

bool diagonally_aligned(const pair<coord, coord> line)
{
  return abs(line.first.first - line.second.first) == abs(line.first.second - line.second.second);
}

void draw_vertical_line(map<coord, int> &positions, pair<coord, coord> line)
{
  const int x = line.first.first; // == line.second.first
  const int y1 = std::min(line.first.second, line.second.second);
  const int y2 = std::max(line.first.second, line.second.second);
  for (int i = y1; i <= y2; i++)
  {
    positions[std::make_pair(x, i)] += 1;
  }
}

void draw_horizontal_line(map<coord, int> &positions, pair<coord, coord> line)
{
  const int y = line.first.second; // == line.second.second
  const int x1 = std::min(line.first.first, line.second.first);
  const int x2 = std::max(line.first.first, line.second.first);
  for (int i = x1; i <= x2; i++)
    positions[std::make_pair(i, y)] += 1;
}

void draw_diagonal_line(map<coord, int> &positions, pair<coord, coord> l)
{
  int steps = abs(l.second.first - l.first.first);
  int x_dir = l.second.first - l.first.first > 0 ? 1 : -1;
  int y_dir = l.second.second - l.first.second > 0 ? 1 : -1;
  for (int i = 0; i <= steps; i++)
  {
    int x = l.first.first + (x_dir * i);
    int y = l.first.second + (y_dir * i);
    positions[std::make_pair(x, y)] += 1;
  }
}

int count_overlaps(const map<coord, int> &positions)
{
  int count = 0;
  for (auto const &val : positions)
  {
    if (val.second > 1)
      count += 1;
  }
  return count;
}

void solve(const vector<string> &input, const bool part_b = false)
{
  vector<pair<coord, coord>> lines = parse_data(input);
  map<coord, int> positions;
  for (pair<coord, coord> l : lines)
  {
    if (horizontally_aligned(l))
      draw_vertical_line(positions, l);
    else if (vertically_aligned(l))
      draw_horizontal_line(positions, l);
    else if (diagonally_aligned(l) && part_b)
      draw_diagonal_line(positions, l);
  }
  std::cout << "Number of overlaps > " << count_overlaps(positions) << endl;
}

int main()
{
  std::ifstream file("input.txt");
  vector<string> input;
  string line;
  while (std::getline(file, line))
  {
    input.push_back(line);
  }

  // part a
  solve(input);
  // part b
  solve(input, true);

  return 0;
}
