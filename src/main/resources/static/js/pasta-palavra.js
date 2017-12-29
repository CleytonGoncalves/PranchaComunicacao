/* Pede pro servidor criar uma nova palavra, recebendo o modal  */
$("#botao-novo-cadastro").click(function (e) {
    e.preventDefault();

    $.ajax({
        type: "GET",
        url: window.location + "/novo",
        cache: false,
        success: adicionarModal,
        error: function () {
            alert("Falha ao criar novo cadastro.");
        }
    });
});

/* Pede pro servidor a palavra clicada, recebendo o modal  */
$(".botao-editar-cadastro").click(function (e) {
    e.preventDefault();
    var palavraId = $(this).closest("button").attr("id");

    $.ajax({
        type: "GET",
        url: window.location + "/" + palavraId,
        cache: false,
        success: adicionarModal,
        error: function () {
            alert("Falha ao editar o cadastro.");
        }
    });
});

function adicionarModal(modalHtml) {
    $("#modal-cadastro").remove(); // Remove modal se já existir (antigo)
    $("#modal-cadastro-container").append(modalHtml); // Adiciona o modal ao HTML da página
    $("#modal-cadastro").modal(); // Exibe o modal

    adicionarModalListeners();
}

function adicionarModalListeners() {
    /* Listener para habilitar pré-visualização ao selecionar imagem */
    $("#input-imagem-real").change(function () {
        carregarImagemPreview(this);
    });
}

/* Pré-Visualização da imagem escolhida */
function carregarImagemPreview(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#imagem-palavra').attr("src", e.target.result);
        };

        reader.readAsDataURL(input.files[0]);
    }
}
