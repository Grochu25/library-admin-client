@import com.grochu.libraryadminclient.Domain.Author
@import com.grochu.libraryadminclient.Domain.Book
@import java.util.Map
@param Author author
@param Map<Book, int[]> books

@template.layout.main(content = @`
<a href="/authors">Wróć do listy</a><br/>
<div><h1 style="float:left;">Imię: ${author.getName()}</h1><a href="/authors/add?authorId=${author.getId()}" style="float:left;">edytuj</a></div>
<h2 style="clear:both;">Urodzony:
    @if(author.getBirthYear()!=null)
        ${author.getBirthYear()}
    @else
        -
    @endif
</h2>
<h2>Opis:
    @if(author.getBirthYear()!=null)
        <br/>${author.getDescription()}
    @else
        -
    @endif
</h2>

<a href="/books/add?author=${author.getId()}">Dodaj książkę autora</a>
<br/>

<table style="border: solid black 2px;">
    <thead>
    <tr>
        <th>Książka </th>
        <th>Dostępnych kopii </th>
        <th>Łącznie kopii </th>
    </tr>
    </thead>
    <tbody>
    @for(Book book : books.keySet())
        <tr>
            <td><a href="/books/${book.getId()}">${book.getTitle()}</a></td>
            <td style="text-align:center;">${books.get(book)[1]}</td>
            <td style="text-align:center;">${books.get(book)[0]}</td>
        </tr>
    @endfor
    </tbody>
</table>
`)