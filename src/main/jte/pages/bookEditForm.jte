@import com.grochu.libraryadminclient.Domain.Author
@import com.grochu.libraryadminclient.Domain.Book
@import java.util.List
@param List<Author> authors
@param Author preselectedAuthor = null
@param Book book

@template.layout.main(content = @`
<a href="/books/${book.getId()}">Wróć do książki</a>
<h1>Edytuj książke</h1>

<form method="post" action="/books/add/onlyBook">
    <input name="id" type="hidden" value="${book.getId()}"/>
    <label for="title">Tytuł:</label>
    <input id="title" name="title" value="${book.getTitle()}"/>
    <br/>

    <label for="author">Id autora:</label>
    <input list="authorsList" name="author" id="author" value="${book.getAuthor().getId()}"/>
    <datalist id="authorsList">
        @for(Author author : authors)
            <option value="${author.getId()}">${author.getName()}</option>
        @endfor
    </datalist>
    <br/>
    (wpisując imię autora, lista podpowie numer)
    <br/>

    <label for="publishYear">Rok publikacji:</label>
    <input id="publishYear" name="publishYear" type="number" value="${book.getPublishYear()}"/>
    <br/>

    <button type="submit">Dodaj</button>
</form>
`)