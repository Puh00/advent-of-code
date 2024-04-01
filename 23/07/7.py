import re

CARDS: list[str | int] = ["A", "K", "Q", "J", "T", 9, 8, 7, 6, 5, 4, 3, 2]


def parse(file_name: str):
    file = open(file_name, "r")
    hands = list(map(lambda x: re.search("(\w+) (\d+)", x).groups(), file.readlines()))
    file.close()
    return list(map(lambda x: (x[0], int(x[1])), hands))


def count_cards(hand: str, card: str) -> int:
    acc = 0
    for c in hand:
        acc += 1 if c == card else 0
    return acc


def five_of_a_kind(hand: str) -> bool:
    return len(set(hand)) == 1


def four_of_a_kind(hand: str) -> bool:
    for card in hand:
        if count_cards(hand, card) == 4:
            return True
    return False


def full_house(hand: str) -> bool:
    c1 = hand[0]
    c2 = list(filter(lambda x: x != c1, hand))[0]
    num_of_c1 = count_cards(hand, c1)
    num_of_c2 = count_cards(hand, c2)
    return (num_of_c1 == 3 and num_of_c2 == 2) or (num_of_c1 == 2 and num_of_c2 == 3)


def three_of_a_kind(hand: str) -> bool:
    for card in hand:
        if count_cards(hand, card) == 3:
            return True
    return False


# extract common code
def two_pair(hand: str) -> bool:
    pairs = []
    for card in hand:
        if count_cards(hand, card) == 2:
            pairs.append(card)
    return len(set(pairs)) == 2


def one_pair(hand: str) -> bool:
    for card in hand:
        if count_cards(hand, card) == 2:
            return True
    return False


def high_card(hand: str) -> bool:
    return len(set(hand)) == 5


def part_1(plays: list[str]):
    five_of_a_kinds = []
    four_of_a_kinds = []
    full_houses = []
    three_of_a_kinds = []
    two_pairs = []
    one_pairs = []
    high_cards = []

    for hand, _ in plays:
        if five_of_a_kind(hand):
            five_of_a_kinds.append(hand)
        elif four_of_a_kinds(hand):
            four_of_a_kinds.append(hand)
        elif full_house(hand):
            full_houses.append(hand)
        elif three_of_a_kind(hand):
            three_of_a_kinds.append(hand)
        elif two_pair(hand):
            two_pairs.append(hand)
        elif one_pair(hand):
            one_pairs.append(hand)
        elif high_card(hand):
            high_cards.append(hand)
        else:
            raise Exception("hand slipped through all checks")

    ranks = []


def main():
    plays = parse("input.txt")
    part_1(plays)


if __name__ == "__main__":
    main()
