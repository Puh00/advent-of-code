const fs = require("fs");

// Separate groups by 2 consecutive newlines
const groups = fs
  .readFileSync("input.txt", {encoding: "utf-8"})
  .split("\n\n")
  .filter((x) => x);

let p1 = 0;
let p2 = 0;

for (const group of groups) {
  // Concatenate, remove the newlines, [...] turns the string into Array of chars
  const uniques = new Set([...group.replace(/\n/g, "")]);
  p1 += uniques.size;

  // Separate members in a array
  // Filter at the end to make sure it's not an empty string
  const curr = group.split("\n").filter((x) => x);
  console.log(curr);

  p2 += [...uniques].filter((char) => curr.every((form) => form.includes(char)))
    .length;
}

console.log("Part 1: " + p1);
console.log("Part 2: " + p2);
