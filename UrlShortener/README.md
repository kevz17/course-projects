## URL shortener

This URL shortener is built using MERN stack. The server files are in root directory, while the client files are in client folder also located in root. It is deployed by building React (client) and running Node (server) to serve the front-end.

1. Data model and schema with Mongoose
   A URL Schema is created to represent the record of URL ID, long URL, shortened URL, created date, and last updated date information associated with user's input link. The URL ID, long URL, and shortened URL are unique in database. With Mongoose, the APIs provided are easy to use and interact with the database by referencing query documentations. Also, the records of data are expressed in JSON style, which makes it convenient to implement the CRUD operations.

2. Experience to work with databases, challenges and ease in representing data
   I have worked with MySQL relational database using MySQL workbench, which is different from MongoDB with Mongoose. As for MySQL, when creating a table, we need to define primary key or foreign key constraints for later data retrieving, select and project records using "verbose" SQL, while for MongoDB it's easy to work with data returned in an object way by using the APIs provided by Mongoose, which facilitate the coding efficiency. Since I have learned Mongoose from online resources, there is no challenge to represent this relative small data with one table and a few columns.

3. Response to the questions or considerations from the Error Handling and Complications section of the assignment

    - If the same URL (link) is submitted by multiple users, the shortener will return the already existing shortened URL. Since this shortener does not provide users with registration, login, or leveled rights, multiple users in this case is considered as one user submitting multiple times. If this link is not successfully shortened before, the shortener will return a new unique one.

    - If a user requests a branded URL that already exists, a message will pop up with detailed instruction.

    - The data structure in MongoDB is described in Item 1. The branded and unbranded URLs be stored in the same manner. There are no marks or ways to distinguish them at this time.

    - If a user were to supply an invalid URL, or a string that isn’t a URL at all, the server side will validate the URL by using a Node package named valid-url. However, this validation does not ensure that the URL will actually reach to a website, it only checks if it's a well-formed URI.

    - After a user deletes, the user will be prompted with detailed message if this deletion is successful or not. If user tries to delete a record that is not in the database (or delete the same URL associated with unique URL ID twice in the edit page), a message will pop up with detailed instruction without performing any deletions.

    - If users try to edit a URL that doesn’t exist, a message will show with reasons and instructions to do next.

4. Additional features, functionality or design changes given more time

    - Add user authentication and authorization, provide different users with their corresponding rights.

    - Add URL ID editting so that users can modify their own custom ending

    - Redesign RECORD page to display appropriate content instead of displaying all the records in database.

5. Assumptions made on this assignment

    - Users will be informed with the instructions displayed on the landing page's note section.

    - Users do not have the need to modify the URL ID (custom URL ending) in the case of changing minds of a submitted URL ending.

    - Users do not have the need to bid for an already registered custom URL ending.

6. Challenges in this assignment

    - CORS issue when requesting from the same host to server that is the same host. Solved by importing cors Node package. But it took time to figure out where to put the `app.use(cors)` sentence in the server.js file.

    - Rendering components in React: Not familiar enough with how the rendering works especially the return word, which took some time to debug.

    - Preparing for deployment: modify related files and code including environment variables, add deployment scripts in package.json, add configuration variables in Heroku, rearrange the organization of project files for running only Node and serve React on the server.

Reference:

    - Traversy Media https://www.youtube.com/watch?v=KyWaXA_NvT0

    - Nate Jensen https://coursework.vschool.io/deploying-mern-with-heroku/

    - John Au-Yeung https://medium.com/better-programming/using-url-parameters-and-query-strings-with-react-router-fffdcea7a8e9
