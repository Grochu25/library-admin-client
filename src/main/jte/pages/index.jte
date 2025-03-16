@template.layout.main(content = @`
    <h1>Witaj w panelu zarządzania biblioteką!</h1>
    @if(org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication() == null
|| org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
        <a href="/login">Zaloguj</a>
    @else
        <ul>
            <li><a href="/books">Książki</a></li>
            <li><a href="/authors">Autorzy</a></li>
            <li><a href="/customers">Klienci</a></li>
        </ul>
    @endif
`)