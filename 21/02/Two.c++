#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <sstream>
#include <stdexcept>

using std::string;
using std::vector;

// splits the command into a tuple
// i.e "forward 5" --> <"forward", 5>
std::pair<string, int> split_by_space(string s)
{
  std::istringstream split(s);
  vector<string> tokens;
  const char split_char = ' ';
  for (std::string each; std::getline(split, each, split_char); tokens.push_back(each))
    ;
  return std::make_pair(tokens.front(), std::stoi(tokens.back()));
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

  // Part A
  int x = 0, y = 0;

  for (const string s : input)
  {
    const std::pair<string, int> pair = split_by_space(s);
    const string direction = pair.first;
    const int amount = pair.second;

    if (direction == "forward")
      x += amount;
    else if (direction == "down")
      y += amount;
    else if (direction == "up")
      y -= amount;
    else
      throw std::runtime_error("Illegal Argument Exception");
  }

  std::cout << x * y << "\n";

  // Part B
  int aim = 0;
  x = 0, y = 0;

  for (const string s : input)
  {
    const std::pair<string, int> pair = split_by_space(s);
    const string command = pair.first;
    const int amount = pair.second;

    if (command == "forward")
    {
      x += amount;
      y += amount * aim;
    }
    else if (command == "down")
      aim += amount;
    else if (command == "up")
      aim -= amount;
    else
      throw std::runtime_error("Illegal Argument Exception");
  }

  std::cout << x * y << "\n";

  return 0;
}