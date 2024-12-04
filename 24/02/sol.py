def parse():
    with open("input.txt", "r") as file:
        return [list(map(int, line.split())) for line in file.readlines()]


def check_condition(report, comparator):
    return all(
        comparator(report[i], report[i + 1])
        and abs(report[i] - report[i + 1]) > 0
        and abs(report[i] - report[i + 1]) <= 3
        for i in range(len(report) - 1)
    )


def part_1(reports) -> int:
    return sum(
        1
        for report in reports
        if check_condition(report, lambda x, y: x < y) or check_condition(report, lambda x, y: x > y)
    )


def part_2(reports):
    def check_lenient_condition(report, comparator):
        for i in range(len(report)):
            if check_condition(report[:i] + report[i + 1 :], comparator):
                return True
        return False

    return sum(
        1
        for report in reports
        if check_lenient_condition(report, lambda x, y: x < y) or check_lenient_condition(report, lambda x, y: x > y)
    )


def main():
    reports = parse()
    print(part_1(reports))
    print(part_2(reports))


if __name__ == "__main__":
    main()
