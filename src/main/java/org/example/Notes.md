🔍 Step-by-step Explanation
1. img.getRGB(x, y)
   This method returns a single int value that contains the red, green, blue, and alpha (transparency) information all packed into one 32-bit integer like this:
   Bits:  [31-24]     [23-16]   [15-8]   [7-0]
          Alpha         Red      Green    Blue

2. (int) img.getRGB(x, y)
   The cast to int is to make sure you're working with a 32-bit integer. Java usually returns the value as int already, but this ensures consistency.

3. *r = ((rgb >> 16) & 0xFF);*
   This does two things:
   - rgb >> 16 shifts the 32-bit number right by 16 bits, bringing the red component to the last 8 bits.
   - & 0xFF masks out everything except those last 8 bits. 0xFF in binary is 00000000 00000000 00000000 11111111.
   
   So this extracts the red channel value (0–255).
   
4. *g = ((rgb >> 8) & 0xFF);*
   - Shift right 8 bits to bring the green component to the last 8 bits.
   - Mask with 0xFF to keep only those 8 bits.
   
   So this extracts the green value.

5. *b = (rgb & 0xFF);*
   - No shifting here. The blue component is already in the last 8 bits.
   - Mask with 0xFF to extract just the blue value.


# 0xFF
This expression gives the last 8 bits of the byte.