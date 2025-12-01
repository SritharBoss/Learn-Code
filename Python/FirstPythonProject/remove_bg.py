from rembg import remove
from PIL import Image
import os

cwd = os.getcwd()
# Load the image
input_path = cwd + os.sep + 'input.jpg'  # Replace with your image path
output_path = 'output.png'

# Step 1: Remove the background
with open(input_path, 'rb') as input_file:
    image_data = input_file.read()
    result = remove(image_data)

# Save the result to a file
with open(output_path, 'wb') as output_file:
    output_file.write(result)

# Step 2: Open the image with the background removed
img = Image.open(output_path)

# Step 3: Crop the image to the bounding box of the person
img_cropped = img.crop(img.getbbox())

# Step 4: Create a new image with a solid background color
background_color = (255, 0, 0)  # Red background color (change to your preferred color)
new_background = Image.new("RGBA", img_cropped.size, background_color)

# Step 5: Paste the cropped person onto the new background
new_background.paste(img_cropped, (0, 0), img_cropped)

# Step 6: Save the final image
new_background.save(output_path)

print("Image processed and saved as", output_path)
