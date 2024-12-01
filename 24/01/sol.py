from collections import Counter
from typing import List


def parse():
    with open("input.txt", "r") as file:
        l, r = zip(*(map(int, line.split()) for line in file.readlines()))
        return list(l), list(r)


def part_1(l: List[int], r: List[int]):
    l.sort()
    r.sort()
    res = (abs(x - y) for x, y in zip(l, r))
    return sum(res)


def part_2(l: List[int], r: List[int]):
    r_freq = Counter(r)
    return sum(x * r_freq[x] for x in l)


def main():
    l, r = parse()
    print(part_1(l, r))
    print(part_2(l, r))


if __name__ == "__main__":
    main()
