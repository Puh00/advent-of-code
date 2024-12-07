import re


def parse():
    with open("input.txt", "r") as file:
        return file.read()


def part_1(line):
    return sum(int(match.group(1)) * int(match.group(2)) for match in re.finditer(r"mul\((\d+),(\d+)\)", line))


def part_2(line):
    return part_1(re.sub(r"don't\(\).*?(?=do\(\)|$)", "", line, flags=re.DOTALL))


def main():
    line = parse()
    print(part_1(line))
    print(part_2(line))


if __name__ == "__main__":
    main()
