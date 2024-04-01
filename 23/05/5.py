import re


def parse(file_name: str) -> tuple[list[int], list[list[int]]]:
    def parse_nums(block: str) -> list[int]:
        return [int(x) for x in re.findall(r"(\d+)", block)]

    f = open(file_name, "r")
    blocks = f.read().split("\n\n")
    f.close()
    # seeds
    seeds = parse_nums(blocks.pop(0))
    # maps
    maps = []
    for block in blocks:
        tmp = []
        for line in block.split("\n")[1:]:
            tmp.append(parse_nums(line))
        maps.append(tmp)
    return (seeds, maps)


def solve(seed, maps):
    for m in maps:
        for dest, source, range_length in m:
            if seed >= source and seed < source + range_length:
                seed = seed - source + dest
                break
    return seed


def main():
    seeds, maps = parse("input.txt")
    # part 1
    print(min([solve(seed, maps) for seed in seeds]))
    # part 2
    seed_ranges = [seeds[i : i + 2] for i in range(0, len(seeds), 2)]
    print(seed_ranges)
    min_location = float("inf")
    #for start, end in seed_ranges:
    #    for i in range(start, end):
    #        location = solve(i, maps)
    #        min_location = location if location < min_location else min_location


if __name__ == "__main__":
    main()
