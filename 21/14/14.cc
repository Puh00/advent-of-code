#include <fstream>
#include <iostream>
#include <map>
#include <regex>
#include <string>

void expand(std::map<std::string, char>& policy, std::string polymer,
            int const iterations) {
  // insertions
  for (int i = 0; i < iterations; i++) {
    std::cout << "iter: " << i << std::endl;
    auto it = polymer.begin();
    while (it != polymer.end()) {
      std::string pair{*it, *std::next(it)};
      if (!std::isblank(policy[pair]))
        it = polymer.insert(std::next(it), policy[pair]);
      it++;
    }
  }

  // frequency table
  std::map<char, long long> freq;
  for (auto const c : polymer) freq[c]++;

  // max, min
  long long max = 0;
  int min = INT_MAX;
  for (auto const& kv : freq) {
    if (kv.second > max) max = kv.second;
    if (kv.second < min) min = kv.second;
  }

  std::cout << (max - min) << std::endl;
}

int main() {
  static std::regex re{R"((\w+) -> (\w))"};
  std::ifstream file("input.txt");
  std::string line;
  std::getline(file, line);
  std::string polymer_template = line;

  std::map<std::string, char> policy;
  while (std::getline(file, line)) {
    std::smatch m;
    if (std::regex_search(line, m, re)) {
      policy[m.str(1)] = m.str(2)[0];
    }
  }
  expand(policy, polymer_template, 10);

  return 0;
}