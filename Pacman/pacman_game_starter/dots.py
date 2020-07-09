from dot import Dot


class Dots:
    """A collection of dots."""
    def __init__(self, WIDTH, HEIGHT,
                 LEFT_VERT, RIGHT_VERT,
                 TOP_HORIZ, BOTTOM_HORIZ):
        self.WIDTH = WIDTH
        self.HEIGHT = HEIGHT
        self.TH = TOP_HORIZ
        self.BH = BOTTOM_HORIZ
        self.LV = LEFT_VERT
        self.RV = RIGHT_VERT
        self.SPACING = 75
        self.EAT_DIST = 50
        # Initialize four rows of dots, based on spacing and width of the maze
        self.top_row = [Dot(self.SPACING * i, self.TH)
                        for i in range(self.WIDTH//self.SPACING + 1)]
        self.bottom_row = [Dot(self.SPACING * i, self.BH)
                           for i in range(self.WIDTH//self.SPACING + 1)]
        self.left_col = [Dot(self.LV, self.SPACING * i)
                         for i in range(self.HEIGHT//self.SPACING + 1)]
        self.right_col = [Dot(self.RV, self.SPACING * i)
                          for i in range(self.HEIGHT//self.SPACING + 1)]

    def display(self):
        """Calls each dot's display method"""
        for i in range(0, len(self.top_row)):
            self.top_row[i].display()
        for i in range(0, len(self.bottom_row)):
            self.bottom_row[i].display()
        for i in range(0, len(self.left_col)):
            self.left_col[i].display()
        for i in range(0, len(self.right_col)):
            self.right_col[i].display()

    # TODO:
    # PROBLEM 3: implement dot eating
    # BEGIN CODE CHANGES
    def eat(self, x, y):  # You might want/need to pass arguments here.
        """Implement dots eating based on the location of pac-man"""
        # All the dots except the dots specified below will be eaten
        # when the distance between pac-man and the dot is less than or
        # equal to the eating distance or EAT_DIST.
        # The remaining eaten dots are the ones pac-man encourters
        # after pac-man moves off the edge and appears to the other side
        # of the canvas (the dots on the edge that pac-man first
        # encounters are not included). These dots will be eaten when
        # pac-man's location (x, y) is the same as the dots (not a range
        # like EAT_DIST).
        for dot in self.top_row:
            if ((dot.x - x)**2 + (dot.y - y)**2)**0.5 <= self.EAT_DIST:
                self.top_row.remove(dot)
            elif y == self.TH and abs(dot.x - x) == self.WIDTH:
                self.top_row.remove(dot)
        for dot in self.bottom_row:
            if ((dot.x - x)**2 + (dot.y - y)**2)**0.5 <= self.EAT_DIST:
                self.bottom_row.remove(dot)
            elif y == self.BH and abs(dot.x - x) == self.WIDTH:
                self.bottom_row.remove(dot)
        for dot in self.left_col:
            if ((dot.x - x)**2 + (dot.y - y)**2)**0.5 <= self.EAT_DIST:
                self.left_col.remove(dot)
            elif x == self.LV and abs(dot.y - y) == self.HEIGHT:
                self.left_col.remove(dot)
        for dot in self.right_col:
            if ((dot.x - x)**2 + (dot.y - y)**2)**0.5 <= self.EAT_DIST:
                self.right_col.remove(dot)
            elif x == self.RV and abs(dot.y - y) == self.HEIGHT:
                self.right_col.remove(dot)
        return [self.top_row, self.bottom_row,
                self.left_col, self.right_col]
    # END CODE CHANGES

    def dots_left(self):
        """Returns the number of remaing dots in the collection"""
        return (len(self.top_row) +
                len(self.bottom_row) +
                len(self.left_col) +
                len(self.right_col))
