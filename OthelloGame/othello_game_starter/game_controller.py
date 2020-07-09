class GameController:
    """Maintains the state of the game"""
    def __init__(self, WIDTH, HEIGHT, SIZE, ADD_HEIGHT):
        self.WIDTH = WIDTH
        self.HEIGHT = HEIGHT
        self.SIZE = SIZE
        self.ADD_HEIGHT = ADD_HEIGHT
        self.player_wins = False
        self.computer_wins = False
        self.ties = False
        self.counts = [0, 0]  # Stores counts of black and white tiles
        self.loop = 0  # Records number of calls of draw() in starter file

    def update(self):
        """Carries out necessary actions if a tie appears or one side wins"""
        if self.ties:
            self.print_text("A TIE")
        if self.computer_wins:
            self.print_text("COMPUTER WINS")
        if self.player_wins:
            self.print_text("YOU WIN !!!")

    def print_text(self, a_string):
        """
        Inputs a string argument, prints the game result and the
        number of tiles on each side.
        """
        # The codes of this block is the same as the draw_info_board()
        # method of class board except the first argument of text()
        TEXT_PERC = 0.2  # Size of text to a spot block
        VERTICAL_OFFSET = 0.55  # Offsets vertical position of text
        noStroke()
        rectMode(CENTER)
        fill(0.2)
        rect(self.WIDTH/2, self.HEIGHT+self.ADD_HEIGHT/2,
             self.WIDTH/self.SIZE*2, self.ADD_HEIGHT/2)
        textSize(self.ADD_HEIGHT * TEXT_PERC)
        textAlign(CENTER)
        fill(1)
        text(a_string, self.WIDTH/2,
             self.HEIGHT+self.ADD_HEIGHT*VERTICAL_OFFSET)
        self.handle_prompt_window()

    def handle_prompt_window(self):
        """
        Waits for about one second to prompt users an input dialog.
        Also, spares time for board to display the last result on board.
        """
        self.loop += 1
        if self.loop == 60:
            self.enter_info()

    def enter_info(self):
        """
        When game is over, prompts the users for their names and save
        their names and number of black tiles on the board in a file.
        """
        if self.ties or self.player_wins or self.computer_wins:
            answer = self.input('enter your name')
            if answer:
                self.edit_txt(answer)

    def input(self, message=''):
        """Prompts a dialog to extract user input"""
        from javax.swing import JOptionPane
        return JOptionPane.showInputDialog(frame, message)

    def edit_txt(self, answer):
        """
        Writes user name and scores into a txt file. Puts them
        to the first line, if the score is the hightest.
        """
        with open("scores.txt", "a") as f:
            f.write(answer + " " + str(self.counts[0]) + "\n")
        with open("scores.txt", "r+") as f:
            contents = f.readlines()
            if len(contents) > 1:
                if int(contents[-1].split()[-1])\
                        > int(contents[0].split()[-1]):
                    new_name = contents.pop()
                    contents.insert(0, new_name)
                    new_contents = "".join(contents)
                    f.seek(0)
                    f.truncate()
                    f.write(new_contents)
