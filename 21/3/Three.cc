#include <iostream>
#include <fstream>
#include <vector>
#include <string>

using std::string;
using std::vector;

// flip the bits in a string
string flip_bits(string b)
{
  string flipped = "";
  for (char c : b)
  {
    if (c == '1')
      flipped.append("0");
    else
      flipped.append("1");
  }
  return flipped;
}

// counts the occurences of 1 in each position for all rows
vector<int> count_bits(vector<string> input, int bit_length)
{
  // initialize array of size bit_length, default values 0
  vector<int> count(bit_length, 0);

  for (string bits : input)
  {
    for (int i = 0; i < bit_length; i++)
    {
      // subtract '0' to convert char to int
      count[i] += bits[i] - '0';
    }
  }
  return count;
}

void part_a(vector<string> input)
{
  const int bit_length = input.front().length();

  vector<int> count = count_bits(input, bit_length);

  // append most common bit in each position
  string binary = "";
  for (int i : count)
  {
    if (i > input.size() / 2)
      binary.append("1");
    else
      binary.append("0");
  }

  int gamma = std::stoi(binary, nullptr, 2);
  int epsilon = std::stoi(flip_bits(binary), nullptr, 2);
  std::cout << "gamma * epsilon > " << gamma * epsilon << "\n";
}

string bit_criteria(vector<string> input, char b_0, char b_1)
{
  const int bit_length = input.front().length();

  vector<int> count = count_bits(input, bit_length);

  for (int i = 0; i < bit_length; i++)
  {
    char bit_criteria;
    count = count_bits(input, bit_length);
    if (count[i] >= input.size() - count[i])
      bit_criteria = b_0;
    else
      bit_criteria = b_1;

    vector<string> temp;
    for (string s : input)
    {
      if (s[i] == bit_criteria)
      {
        temp.push_back(s);
      }
    }
    if (temp.empty())
      break;
    input = temp;
  }
  return input.front();
}

void part_b(vector<string> input)
{
  int oxygen = std::stoi(bit_criteria(input, '1', '0'), nullptr, 2);
  int co2 = std::stoi(bit_criteria(input, '0', '1'), nullptr, 2);
  std::cout << "oxy * co2 > " << oxygen * co2 << "\n";
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

  part_a(input);
  part_b(input);

  return 0;
}