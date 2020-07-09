class Tile:
    """A tile"""
    def __init__(self, row, column, WIDTH, HEIGHT, SIZE, isblack=True):
        self.PERCENTAGE = 0.85  # The ratio of tile size to spot size
        self.SPACING = WIDTH/SIZE
        self.x = column*self.SPACING + self.SPACING/2
        self.y = row*self.SPACING + self.SPACING/2
        self.tile_width = WIDTH/SIZE * self.PERCENTAGE
        self.tile_height = HEIGHT/SIZE * self.PERCENTAGE
        self.isblack = isblack

    def display(self):
        """Draws a tile in black or white"""
        # The default color of a tile is black
        STROKE_COLOR = 0  # Black
        STROKE_WEIGHT = 1
        FILL_COLOR_BL = 0  # Black
        FILL_COLOR_WH = 1  # White
        stroke(STROKE_COLOR)
        strokeWeight(STROKE_WEIGHT)
        if self.isblack:
            fill(FILL_COLOR_BL)
            ellipse(self.x, self.y, self.tile_width, self.tile_height)
        else:
            fill(FILL_COLOR_WH)
            ellipse(self.x, self.y, self.tile_width, self.tile_height)
