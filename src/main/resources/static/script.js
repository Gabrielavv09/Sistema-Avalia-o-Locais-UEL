// Exemplo de função para carregar as questões
async function carregarQuestoes() {
    const response = await fetch('/api/questoes');
    const questoes = await response.json();

    const questaoList = document.getElementById('questao-list');

    questoes.forEach(questao => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${questao.id}</td>
            <td>${questao.titulo}</td>
            <td>
                <a href="/questoes/editar/${questao.id}">Editar</a> | 
                <a href="/questoes/excluir/${questao.id}">Excluir</a>
            </td>
        `;
        questaoList.appendChild(tr);
    });
}

document.addEventListener('DOMContentLoaded', carregarQuestoes);
