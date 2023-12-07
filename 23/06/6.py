import re


def parse(file_name: str) -> list[tuple[int, int]]:
    file = open(file_name, "r")
    times, distances = map(
        lambda x: list(map(lambda y: int(y), re.findall("(\d+)", x))), file.readlines()
    )
    file.close()
    return list(zip(times, distances))


# binary search using the given condition
def binary_search(left: int, right: int, condition):
    m = -1
    while left <= right:
        m = int((right - left) / 2) + left
        result = condition(m)
        if result == 0:
            break
        elif result == -1:
            left = m + 1
        else:
            right = m - 1
    return m


def find_range(time: int, distance: int):
    # condition for finding the first time that yields 
    # a distance better than the specified one
    def first_time_condition(t: int) -> int:
        prev = (t - 1) * (time - (t - 1))
        curr = t * (time - t)

        if prev <= distance and curr > distance:
            # match
            return 0
        elif prev <= distance and curr <= distance:
            return -1
        elif prev > distance and curr > distance:
            return 1

    # condition for finding the last time that yields
    # a distance better than the specified one
    def last_time_condition(t: int) -> int:
        curr = t * (time - t)
        succ = (t + 1) * (time - (t + 1))

        if curr > distance and succ <= distance:
            # match
            return 0
        elif curr <= distance and succ <= distance:
            return 1
        elif curr > distance and succ > distance:
            return -1

    mid = int(time / 2)
    first = binary_search(0, mid, first_time_condition)
    last = binary_search(mid + 1, time, last_time_condition)
    return first, last


def part_1(races):
    acc = 1
    for time, distance in races:
        first, last = find_range(time, distance)
        acc *= last - first + 1
    print("Part 1: ", acc)


def part_2(races: list[tuple[int, int]]):
    # fix input
    time, distance = "", ""
    for t, d in races:
        time += str(t)
        distance += str(d)
    time = int(time)
    distance = int(distance)
    # calculate number of possibilities
    first, last = find_range(time, distance)
    print("Part 2: ", last - first + 1)


def main():
    races = parse("input.txt")
    part_1(races)
    part_2(races)


if __name__ == "__main__":
    main()
