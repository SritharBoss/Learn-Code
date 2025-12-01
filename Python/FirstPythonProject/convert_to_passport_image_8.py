import os

from PIL import Image

# Open the image you want to convert
input_image = Image.open('input.jpg')

# Define the dimensions in inches (1.5x2 inches)
passport_size_in_inches = (1.5, 2)

# Define the DPI (dots per inch), commonly 300 DPI for photos
dpi = 300

# Calculate the target size in pixels (width, height)
passport_size_in_pixels = (int(passport_size_in_inches[0] * dpi), int(passport_size_in_inches[1] * dpi))

# Resize the image to passport size
resized_image = input_image.resize(passport_size_in_pixels)

# Create a new blank 4x6 inch image
canvas_size_in_inches = (6, 4)
canvas_size_in_pixels = (int(canvas_size_in_inches[0] * dpi), int(canvas_size_in_inches[1] * dpi))

# Create a white background (255, 255, 255) 4x6 inch canvas
canvas = Image.new('RGB', canvas_size_in_pixels, (255, 255, 255))

# Paste the resized passport image 8 times on the canvas in 4x2 grid
for row in range(2):
    for col in range(4):
        # Calculate the x and y positions for each image
        x_offset = col * passport_size_in_pixels[0]
        y_offset = row * passport_size_in_pixels[1]
        # Paste the image at the calculated position
        canvas.paste(resized_image, (x_offset, y_offset))

# Save the final image as a 4x6 inch ready-for-print image
canvas.save('output_image_8.jpg', dpi=(dpi, dpi))

# Display the final image
canvas.show()

os.system('pause')
