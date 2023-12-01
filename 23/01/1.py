import re

FIRST_DIGIT_REGEX_P1: str = r"\D*(\d).*"
LAST_DIGIT_REGEX_P1: str = r".*(\d)\D*"
FIRST_DIGIT_REGEX_P2: str = r"(\d|one|two|three|four|five|six|seven|eight|nine).*"
LAST_DIGIT_REGEX_P2: str = r".*(\d|one|two|three|four|five|six|seven|eight|nine)"

DIGITS = {
    "one": "1",
    "two": "2",
    "three": "3",
    "four": "4",
    "five": "5",
    "six": "6",
    "seven": "7",
    "eight": "8",
    "nine": "9",
}


def part_1(line: str) -> int:
    (d1,) = re.search(FIRST_DIGIT_REGEX_P1, line).groups()
    (d2,) = re.search(LAST_DIGIT_REGEX_P1, line).groups()
    return int(d1 + d2)


def part_2(line: str) -> int:
    (d1,) = re.search(FIRST_DIGIT_REGEX_P2, line).groups()
    (d2,) = re.search(LAST_DIGIT_REGEX_P2, line).groups()
    d1 = d1 if d1.isdigit() else DIGITS[d1]
    d2 = d2 if d2.isdigit() else DIGITS[d2]
    return int(d1 + d2)


def main(part_1=False):
    acc: int = 0
    with open("input.txt", "r") as file:
        for line in file:
            acc += part_1(line) if part_1 else part_2(line)
    print(acc)


if __name__ == "__main__":
    main()
