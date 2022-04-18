/**
 * Author:      Lucas Larsson
 * Date:        2022-04-17
 * Description: This program was implemented as a JS test only
 *
 **/

// Javascript Implementation to find the
// XOR of the two Binary Strings

// Function to find the
// XOR of the two Binary Strings
function xoring(a, b, n){
    let ans = "";

    // Loop to iterate over the
    // Binary Strings
    for (let i = 0; i < n; i++)
    {

        if (a[i] !== b[i]) ans += "1";
        // Any difference?
        // Above approach gives number of unmatched bits directly, below approach  appends 0 where it is matched,
        // benefit of below is that it keeps a string of 256 length if you want that .i.e to keep it in the same-length.
        /**
         *  if (a[i] === b[i])
         *             ans += "0";
         *         else
         *             ans += "1";
         */
    }
    return ans;
}

// Still trying to get it work with a annaonyms function
// check link: https://www.w3schools.com/jsref/jsref_foreach.asp
// const c = xoring(a, b, n);
// console.log(c.length);
// let count = 0;
// function Count (item){ if(item[i] === '1') count++;}
// c.forEach(Count)


// Driver Code
let a = "1001010111000100000111011110001110001011100000000010111001111101011011001000101111110110000010110101010111001101110100110101110011110100101010100111111010110010100111010101010110000000101011111110111000000111100101001001001001001101111101110111110010001111";
let b = "1100010111000110111010111110011000001111111000011000011111101110100101110100100111100000000011011011101110110001100001001110101010001010111111100110010110100111100011110101111000111001100111101100111011100110110001011011010101111000010100111010101011101010";
let n = a.length;
let c = xoring(a, b, n);
console.log(c.length);
let count = 0;
for (let i = 0; i < c.length; i++){
    if(c[i] === '1') count++;
}
console.log(count);
console.log(c);