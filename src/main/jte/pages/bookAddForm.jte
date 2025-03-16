@import com.grochu.libraryadminclient.DAL.Author
@import java.util.List
@param List<Author> authors
@param Author preselectedAuthor = null

@template.layout.main(content = @`
@if(preselectedAuthor == null)
    <a href="/books">Wróć do listy</a>
    <h1>Dodaj nową książke</h1>
@else
    <a href="/authors/${preselectedAuthor.getId()}">Wróć do autora</a>
    <h1>Dodaj nową książke autora: ${preselectedAuthor.getName()}</h1>
@endif

<form method="post" action="/books/add/onlyBook">
    <label for="title">Tytuł:</label>
    <input id="title" name="title"/>
    <br/>

    <label for="author">Id autora:</label>
    @if(preselectedAuthor == null)
    <input list="authorsList" name="author" id="author"/>
    <datalist id="authorsList">
        @for(Author author : authors)
            <option value="${author.getId()}">${author.getName()}</option>
        @endfor
    </datalist>
    @else
        <input name="author" id="author" readonly="true" value="${preselectedAuthor.getId()}"/>
    @endif
    <a href="add?author=new">Dodaj autora spoza listy</a>
    <br/>
    (wpisując imię autora, lista podpowie numer)
    <br/>

    <label for="publishYear">Rok publikacji:</label>
    <input id="publishYear" name="publishYear" type="number"/>
    <br/>

    <button type="submit">Dodaj</button>
</form>
`)