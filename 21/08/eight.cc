#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <algorithm>
#include <set>
#include <iterator>
#include <map>
#include <string>

using std::endl;
using std::map;
using std::set;
using std::string;
using std::vector;

struct entry
{
  vector<string> lhs;
  vector<string> rhs;
  entry(vector<string> l, vector<string> r)
  {
    lhs = l;
    rhs = r;
  }
};

vector<string> split(const string s, const char delimiter)
{
  std::istringstream ss(s);
  vector<string> tokens;
  string token;

  while (std::getline(ss, token, delimiter))
    if (!token.empty())
      tokens.push_back(token);
  return tokens;
}

vector<entry> parse_data(const vector<string> &input)
{
  vector<entry> parsed;
  for (auto s : input)
  {
    const auto tmp = split(s, '|');
    parsed.emplace_back(entry(split(tmp.front(), ' '), split(tmp.back(), ' ')));
  }
  return parsed;
}

// union of two char sets
set<char> _union(const set<char> &a, set<char> &b)
{
  set<char> unioned;
  std::set_union(a.begin(), a.end(), b.begin(), b.end(), std::inserter(unioned, unioned.begin()));
  return unioned;
}

// returns the size of the difference of two vectors<char>
int _diff_size(const set<char> &a, const set<char> &b)
{
  set<char> diff;
  std::set_difference(a.begin(), a.end(), b.begin(), b.end(), std::inserter(diff, diff.begin()));
  return diff.size();
}

// the diff of 3 with union of 2 and 5 should be an empty set
set<char> find_3(vector<set<char>> &segs)
{
  if (_diff_size(segs[0], _union(segs[1], segs[2])) == 0)
    return segs[0];
  else if (_diff_size(segs[1], _union(segs[0], segs[2])) == 0)
    return segs[1];
  else
    return segs[2];
}

// only three have six characters, namely 0, 6, 9
// if the diff of 3 with a digit is 0 then it must be 9
set<char> find_9(const vector<set<char>> &segs, set<char> three)
{
  for (auto char_set : segs)
    if (_diff_size(three, char_set) == 0)
      return char_set;
  throw std::runtime_error("Couldn't find any nine");
}

std::pair<set<char>, set<char>> find_2_5(const vector<set<char>> &segs, set<char> nine)
{
  // if digit diff with 9 is an empty set then it is 5
  const set<char> five = _diff_size(segs.front(), nine) == 0 ? segs.front() : segs.back();
  // the other is 2
  const set<char> two = _diff_size(segs.front(), nine) != 0 ? segs.front() : segs.back();
  return {two, five};
}

std::pair<set<char>, set<char>> find_0_6(const vector<set<char>> &segs, set<char> five)
{
  // if 5 diff with a digit is an empty set then it must be 6
  const set<char> six = _diff_size(five, segs.front()) == 0 ? segs.front() : segs.back();
  // the other is 0
  const set<char> zero = _diff_size(five, segs.front()) != 0 ? segs.front() : segs.back();
  return {zero, six};
}

// map each pattern to the length of the string
map<int, vector<set<char>>> map_pattern_to_length(const vector<string> &patterns)
{
  map<int, vector<set<char>>> m;
  for (string s : patterns)
  {
    if (m.count(s.size()))
      m[s.size()].emplace_back(set<char>(s.begin(), s.end()));
    else
      m[s.size()] = {set<char>(s.begin(), s.end())};
  }
  return m;
}

// decode each pattern
map<set<char>, int> decode_input(const vector<string> &lhs)
{
  auto m = map_pattern_to_length(lhs);
  map<set<char>, int> decoded;

  // numbers with unique size
  decoded[m[2].front()] = 1;
  decoded[m[4].front()] = 4;
  decoded[m[3].front()] = 7;
  decoded[m[7].front()] = 8;

  // three
  const auto three = find_3(m[5]);
  decoded[three] = 3;
  // remove three from patterns with size 5
  m[5].erase(std::remove(m[5].begin(), m[5].end(), three), m[5].end());

  // nine
  const auto nine = find_9(m[6], three);
  decoded[nine] = 9;
  m[6].erase(std::remove(m[6].begin(), m[6].end(), nine), m[6].end());

  // two and five
  const auto two_and_five = find_2_5(m[5], nine);
  decoded[two_and_five.first] = 2;
  const auto five = two_and_five.second;
  decoded[five] = 5;

  // zero and six
  const auto zero_and_six = find_0_6(m[6], five);
  decoded[zero_and_six.first] = 0;
  decoded[zero_and_six.second] = 6;

  return decoded;
}

// only part b
void solve(const vector<string> &input)
{
  const auto parsed = parse_data(input);
  long long sum = 0;

  for (auto const entry : parsed)
  {
    map<set<char>, int> decoded = decode_input(entry.lhs);
    string output_value = "";
    for (auto const pattern : entry.rhs)
      output_value += std::to_string(decoded[set<char>(pattern.begin(), pattern.end())]);
    sum += std::stoi(output_value);
  }
  std::cout << "Sum of output values > " << sum << endl;
}

int main()
{
  std::ifstream file("input.txt");
  vector<string> input;
  for (string line; std::getline(file, line);)
    input.emplace_back(line);

  solve(input);

  return 0;
}
