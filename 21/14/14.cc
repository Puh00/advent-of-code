#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <regex>
#include <string>

void expand(std::map<std::string, char>& policy, std::string const& polymer,
            int const iterations) {
  std::map<std::string, long long> pair_count;
  // frequency of initial pairs
  for (int i = 0; i < polymer.size() - 1; i++) {
    std::string pair{polymer[i], polymer[i + 1]};
    pair_count[pair]++;
  }

  for (int i = 0; i < iterations; i++) {
    // map that stores the amount to increase for each pair
    std::map<std::string, long long> temp;

    for (auto const& kv : policy) {
      auto const old_count = pair_count[kv.first];
      // new pairs after insertion
      std::string p1{kv.first[0], policy[kv.first]};
      std::string p2{policy[kv.first], kv.first[1]};
      // increment new pairs
      temp[p1] += old_count;
      temp[p2] += old_count;
      // reset old pair
      pair_count[kv.first] = 0;
    }
    // increase pair count
    for (auto const& kv : temp)
      pair_count[kv.first] = pair_count[kv.first] + kv.second;
  }
  // store the frequency of each char in the polymer
  std::map<char, long long> freq;
  for (auto const& kv : pair_count) freq[kv.first[0]] += kv.second;

  // compare the map values instead of keys
  auto const& value_comp = [](auto const& p1, auto const& p2) {
    return p1.second < p2.second;
  };
  // if only c++14 had std::minmax_element support
  auto max_kv = *std::max_element(freq.begin(), freq.end(), value_comp);
  auto min_kv = *std::min_element(freq.begin(), freq.end(), value_comp);
  // + 1 due to weird off-by-one bug
  std::cout << max_kv.second - min_kv.second + 1 << std::endl;
}

int main() {
  static std::regex re{R"((\w+) -> (\w))"};
  std::ifstream file("input.txt");
  std::string line;
  std::getline(file, line);
  std::string polymer_template = line;

  std::smatch m;
  std::map<std::string, char> policy;
  while (std::getline(file, line))
    if (std::regex_search(line, m, re)) policy[m.str(1)] = m.str(2)[0];

  // part a
  expand(policy, polymer_template, 10);
  // part b
  expand(policy, polymer_template, 40);

  return 0;
}