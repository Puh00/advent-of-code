#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <sstream>
#include <deque>
#include <algorithm>

using std::endl;
using std::pair;
using std::string;
using std::vector;

using bingo = vector<vector<pair<int, bool>>>;

// splits string of numbers by the delimiter (also adjacent delimiters)
vector<int> split_by_delimiter(const string s, const char delimiter)
{
  std::istringstream ss(s);
  vector<int> tokens;
  string token;

  while (std::getline(ss, token, delimiter))
  {
    if (!token.empty())
      tokens.push_back(std::stoi(token));
  }
  return tokens;
}

pair<vector<int>, vector<bingo>> parse_data(std::deque<string> input)
{
  vector<int> numbers = split_by_delimiter(input.front(), ',');
  // first two rows irrelevant
  input.pop_front();
  input.pop_front(); // will cause segmentation fault if commented out

  vector<bingo> boards;
  bingo board;

  for (string s : input)
  {
    if (s.empty())
    {
      // end of a board has been reached
      boards.push_back(board);
      board.clear();
      continue;
    }
    // convert to vector of pairs
    vector<pair<int, bool>> row;
    for (int i : split_by_delimiter(s, ' '))
    {
      row.push_back(std::make_pair(i, false));
    }
    board.push_back(row);
  }
  // manually push last board
  boards.push_back(board);

  return std::make_pair(numbers, boards);
}

// const reference disallows modifcation
bool is_bingo(const vector<vector<pair<int, bool>>> &board)
{
  const int height = board.size();
  const int width = board[0].size();
  bool marked = true;

  // check rows
  for (int y = 0; y < height; y++)
  {
    for (int x = 0; x < width; x++)
    {
      if (!board[y][x].second)
      {
        marked = false;
        break;
      }
    }
    if (marked)
      return true;
    // reset bool
    marked = true;
  }

  // check columns
  for (int x = 0; x < width; x++)
  {
    for (int y = 0; y < height; y++)
    {
      if (!board[y][x].second)
      {
        marked = false;
        break;
      }
    }
    if (marked)
      return true;
    // reset bool
    marked = true;
  }
  // no rows or cols that are completely marked
  return false;
}

// marks an element if it matches with the given number
void mark_matching_element(bingo &board, int num)
{
  for (auto &row : board)
  {
    for (auto &p : row)
    {
      if (p.first == num)
        p.second = true;
    }
  }
}

void pretty_print_board(const bingo &board)
{
  for (auto const &row : board)
  {
    for (auto const &p : row)
      std::cout << p.first << '(' << p.second << ") ";
    std::cout << endl;
  }
  std::cout << endl;
}

int calculate_score(const bingo &board, int curr_num)
{
  int sum = 0;
  for (auto &row : board)
    for (auto &p : row)
      if (!p.second)
        sum += p.first;
  return sum * curr_num;
}

void solve(std::deque<string> input)
{
  pair<vector<int>, vector<bingo>> data = parse_data(input);
  vector<bingo> &boards = data.second;

  // part a
  bool first = true;
  // parb
  vector<bingo> boards_to_be_deleted;

  for (int num : data.first)
  {
    for (auto &board : boards)
    {
      mark_matching_element(board, num);
      if (boards.size() == 1 && is_bingo(board))
      {
        std::cout << "Last board:" << endl;
        pretty_print_board(boards.front());
        std::cout << "Score > " << calculate_score(boards.front(), num) << endl;
        return;
      }
      if (is_bingo(board))
      {
        if (first)
        {
          // part a
          std::cout << "BINGO!" << endl;
          pretty_print_board(board);
          std::cout << "Score > " << calculate_score(board, num) << endl;
          first = false;
        }
        // boards to be deleted at after iteration
        boards_to_be_deleted.push_back(board);
      }
    }
    // element removal must occur outside of iteration (otherwise segmentation fault)
    for (auto &del : boards_to_be_deleted)
      boards.erase(std::remove(boards.begin(), boards.end(), del), boards.end());
    boards_to_be_deleted.clear();
  }
}

int main()
{
  std::ifstream file("input.txt");
  std::deque<string> input;
  string line;
  while (std::getline(file, line))
  {
    input.push_back(line);
  }
  solve(input);

  return 0;
}
