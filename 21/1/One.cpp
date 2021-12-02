#include <iostream>
#include <fstream>
#include <vector>
#include <string>

using std::string;
using std::vector;

int main()
{
  std::ifstream file("input.txt");
  vector<int> input;
  string line;
  while (std::getline(file, line))
  {
    input.push_back(std::stoi(line));
  }

  // Part A
  int depthCount = 0;

  for (int i = 1; i < input.size(); i++)
  {
    if (input[i] > input[i - 1])
      depthCount++;
  }
  std::cout << depthCount << "\n";

  // Part B
  int windowCount = 0;

  for (int i = 3; i < input.size(); i++)
  {
    if (input[i] + input[i - 1] + input[i - 2] > input[i - 1] + input[i - 2] + input[i - 3])
      windowCount++;
  }
  std::cout << windowCount << "\n";
}