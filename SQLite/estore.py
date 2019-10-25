import sqlite3

# function to enter books into EbookStore
def enter_books():
    id = str(input('Please enter new book ID:\t'))
    title = input('Please enter title of new book:\t')
    author = input('Please enter author of new book:\t')
    qty = str(input('Please enter quantity of new book:\t'))

    query = f'insert into books values({id}, "{title}", "{author}", {qty})'
    return query

# function to update books in the EbookStore
def update_books():
    # ask user to enter ID of book wishing to change
    id_access = str(input(
        'Please enter the book ID you would like to update:\t'))
    # aski user to choose what they would like to update of that book ID
    choice = str(input(
        '1. By ID\n' + '2. By Title\n' +
        '3. By Author\n' + '4. By Quantity\n'))

    # update ID
    if (choice == '1'):
        id = str(input('Please enter new book ID:\t'))
        query = f'update books set id = {id} where id = {id_access}'
        return query

    # update Title
    elif (choice == '2'):
        title = input('Please enter title:\t')
        query = f'update books set Title = "{title}" where id = {id_access}'
        return query

    # update Author
    elif (choice == '3'):
        author = input('Please enter author:\t')
        query = f'update books set Author = "{author}" where id = {id_access}'
        return query

    # update Quantity
    elif (choice == '4'):
        qty = str(input('Please enter new quantity:\t'))
        query = f'update books set Qty = {qty} where id = {id_access}'
        return query
        
    else:
        print('Invalid Option')

# function to delete books from the EbookStore
def delete_books():
    delete_id = str(input(
        'Please enter the ID of book you wish to delete:\t'))
    query = f'delete from books where id = {delete_id}'
    return query

# function to search for a book in the EbookStore


def search_books():
    search = str(input(
        'Would you like to see:\n ' + 
        '1. Entire Book Store\n' +
        '2. Specific Book\t'))
    # search entire store
    if (search == '1'):
        query = f'select * from books'
        return query

    # search for a specific book by ID
    elif (search == '2'):
        id_access = str(input('Enter book ID please:\t'))
        query = f'select from books where id = {id_access}'
        return query

    else:
        print('Invalid Option')


try:
    # creates a file with a SQLite3 database
    db = sqlite3.connect('bookstore.db')
    # create a cursor object and delete table
    cursor = db.cursor()
    cursor.execute('drop table if exists books')
    # create new table
    cursor.execute(
        'create table books' +
        '(id int primary key, Title varchar, Author varchar, Qty int)')

    # records to be entered into database
    records = [
        (3001, 'A Tale of Two Cities', 'Charles Dickens', 30),
        (3002, 'Harry Potter and the Philosopher Stone', 'J.K. Rowling ', 40),
        (3003, 'The Lion, the Witch and the Wardrobe', 'C.S. Lewis', 25),
        (3004, 'The Lord of the Rings', 'J.R.R Tolkien', 37),
        (3005, 'Alice in Wonderland', 'Lewis Carroll', 12)]

    cursor.executemany('insert into books values(?,?,?,?);', records)

    # greet user and ask what they want to do
    print('Welcome to the ebookstore!\n')
    choice = str(input(
        'Would you like to:\n' +
        '1. Enter in a new book\n' +
        '2. Update an existing book\n' +
        '3. Delete an existing books\n' +
        '4. Search database\n' +
        '0. Exit\n'))

    # enter books
    if (choice == '1'):
        print(enter_books())
        cursor.execute(enter_books())

    # update existing books
    elif (choice == '2'):
        print(update_books())
        cursor.execute(update_books())

    # delete books
    elif (choice == '3'):
        print(delete_books())
        cursor.execute(delete_books())

    # search the database
    elif (choice == '4'):
        search = search_books()

        # if equal sign exists in query it means to search for single row
        if ('=' in search):
            cursor.execute(search)
            single_search = cursor.fetchone()
            print(single_search)

        # search entire database
        else:
            cursor.execute(search)
            #all_rows = cursor.fetchall()
            for row in cursor:
                print('{0}, {1}, {2}, {3}'.format(
                    row[0], row[1], row[2], row[3]))

    # exit program
    elif (choice == '0'):
        print('Thank You!\n')

    # commit changes to database
    db.commit()
    print('Thank you, have a good day!\n')

except Exception as e:
    db.rollback()
    raise e

finally:
    # close db object
    db.close()
