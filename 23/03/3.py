# please look away my brain aint working today
import re

NUMBERS_PATTERN = r"(\d+)"
SYMBOLS_PATTERN = r"[^\d.\n]"
GEAR_PATTERN = r"(\*)"


def parse(file_name: str) -> list[str]:
    return open(file_name).read().splitlines()


def valid_index(i, length):
    return i >= 0 and i < length


# retrieves the surrounding coordinates
def adjacent_coordinates(start, end, line_index, lines):
    top = []
    bottom = []
    left = ()
    right = ()
    line_length = len(lines[0])
    # top
    if valid_index(line_index - 1, line_length):
        for i in range(start - 1, end + 2):
            if valid_index(i, line_length):
                top.append(i)
    # bottom
    if valid_index(line_index + 1, line_length):
        for i in range(start - 1, end + 2):
            if valid_index(i, line_length):
                bottom.append(i)
    # left
    if valid_index(start - 1, line_length):
        left = (start - 1, start - 1)
    # right
    if valid_index(end + 1, line_length):
        right = (end + 1, end + 1)

    if len(top) != 0:
        top = (max(0, top[0]), min(line_length, top[-1]))
    if len(bottom) != 0:
        bottom = (max(0, bottom[0]), min(line_length, bottom[-1]))

    # the ranges of the adjacent coordinates
    return (top, left, right, bottom)


# retrieves the matches (regex) that the given ranges are overlapping with
def adjacent_to(top, left, right, bottom, lines, line_index, pattern):
    def overlaps(range_1, range_2):
        return range_1[1] >= range_2[0] and range_1[0] <= range_2[1]

    matches = []
    if top:
        for match in re.finditer(pattern, lines[line_index - 1]):
            if match and overlaps(top, (match.start(), match.end() - 1)):
                matches.append(match)
    if bottom:
        for match in re.finditer(pattern, lines[line_index + 1]):
            if match and overlaps(bottom, (match.start(), match.end() - 1)):
                matches.append(match)
    # left and right
    for match in re.finditer(pattern, lines[line_index]):
        if left and match and overlaps(left, (match.start(), match.end() - 1)):
            matches.append(match)
        if right and match and overlaps(right, (match.start(), match.end() - 1)):
            matches.append(match)
    return matches


def part_1(lines: list[str]):
    acc = 0
    for line_index, line in enumerate(lines):
        for match in re.finditer(NUMBERS_PATTERN, line):
            top, left, right, bottom = adjacent_coordinates(
                match.start(), match.end() - 1, line_index, lines
            )
            matches = adjacent_to(
                top, left, right, bottom, lines, line_index, SYMBOLS_PATTERN
            )
            if len(matches) != 0:
                (num,) = match.groups()
                acc += int(num)
    print(acc)


def part_2(lines: list[str]):
    acc = 0
    for line_index, line in enumerate(lines):
        for match in re.finditer(GEAR_PATTERN, line):
            top, left, right, bottom = adjacent_coordinates(
                match.start(), match.end() - 1, line_index, lines
            )
            matches = adjacent_to(
                top, left, right, bottom, lines, line_index, NUMBERS_PATTERN
            )
            if len(matches) == 2:
                (num1,) = matches[0].groups()
                (num2,) = matches[1].groups()
                acc += int(num1) * int(num2)
    print(acc)


def main():
    lines: list[str] = parse("input.txt")
    part_1(lines)
    part_2(lines)


if __name__ == "__main__":
    main()
