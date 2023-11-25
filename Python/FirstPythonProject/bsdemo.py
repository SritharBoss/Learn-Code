from bs4 import BeautifulSoup

html_content = """
<html>
  <body>
    <p>Hello, World!</p>
    <p>This is a <a href="https://example.com">link</a>.</p>
  </body>
</html>
"""

soup = BeautifulSoup(html_content, 'html.parser')

# Accessing tags
paragraph = soup.find('p')
link = soup.find('a')

# Extracting text and attributes
print(f"Paragraph Text: {paragraph.text}")
print(f"Link Href: {link['href']}")