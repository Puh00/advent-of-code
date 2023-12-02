import re

ID_REGEX: str = r"Game (\d+)"
COLORS_REGEX: str = r"(?:(\d+) (red|green|blue))(?:,|;|$)"

BAG_CONFIGURATION: dict[str, int] = {"red": 12, "green": 13, "blue": 14}


def part_1(line: str) -> int:
    valid: bool = True
    for match in re.finditer(COLORS_REGEX, line):
        num, color = match.groups()
        if int(num) > BAG_CONFIGURATION[color]:
            valid = False
    return int(re.search(ID_REGEX, line).groups()[0]) if valid else 0


def part_2(line: str) -> str:
    max_colors: dict[str, int] = {"red": 0, "green": 0, "blue": 0}
    for match in re.finditer(COLORS_REGEX, line):
        num, color = match.groups()
        if int(num) > max_colors[color]:
            max_colors[color] = int(num)
    return max_colors["red"] * max_colors["green"] * max_colors["blue"]


def main(part_1=False):
    with open("input.txt", "r") as file:
        acc = 0
        for line in file:
            acc += part_1(line) if part_1 else part_2(line)
        print(acc)


if __name__ == "__main__":
    main()
