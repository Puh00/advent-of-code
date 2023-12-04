import re
from math import floor

regex = r"Card\s+(\d+):\s+((?:\d+\s+)+)\|\s+((?:\d+(?:\s+|$))+)"


def parse_line(line: str):
    g = re.search(regex, line).groups()
    return (
        int(g[0]),
        [(i + 1, int(x)) for i, x in enumerate(g[1].split())],
        [int(x) for x in g[2].split()],
    )


def main():
    acc = 0
    scratch_cards = {}
    with open("input.txt", "r") as file:
        for line in file:
            card, winning_numbers, candidates = parse_line(line)
            matches = [(i, x) for (i, x) in winning_numbers if x in candidates]
            # part 1
            acc += floor(2 ** (len(matches) - 1))
            # part 2
            scratch_cards[card] = scratch_cards.get(card, 0) + 1
            # update cards
            for i in range(card + 1, card + len(matches) + 1):
                scratch_cards[i] = scratch_cards.get(i, 0) + scratch_cards[card]

        print("Part 1:", acc)
        print("Part 2:", sum(scratch_cards.values()))


if __name__ == "__main__":
    main()
