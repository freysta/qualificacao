import java.io.File
import java.io.FileWriter
import java.io.IOException

data class Funcionario(
    val nome: String,
    val cargo: String,
    val salario: Double
) {
    override fun toString(): String {
        return "Nome: $nome\nCargo: $cargo\nSalário: R$$salario\n"
    }
}

class CadastroFuncionarios {
    private val funcionarios: MutableList<Funcionario> = mutableListOf()

    fun cadastrarFuncionario() {
        println("Cadastro de Funcionário")
        print("Nome: ")
        val nome = readln() ?: ""
        print("Cargo: ")
        val cargo = readln() ?: ""
        print("Salário: ")
        val salario = readln()?.toDoubleOrNull() ?: 0.0

        val funcionario = Funcionario(nome, cargo, salario)
        funcionarios.add(funcionario)

        println("Funcionário cadastrado com sucesso!")

        salvarDados()
    }

    fun listarFuncionarios() {
        if (funcionarios.isEmpty()) {
            println("Nenhum funcionário cadastrado.")
        } else {
            println("Lista de Funcionários:")
            println("---------------------------------------")
            for (funcionario in funcionarios) {
                println(funcionario.toString())
                println("---------------------------------------")
            }
        }
    }

    fun excluirFuncionario() {
        println("Exclusão de Funcionário")
        print("Digite o nome do funcionário a ser excluído: ")
        val nome = readln() ?: ""

        val funcionarioEncontrado = funcionarios.find { it.nome == nome }
        if (funcionarioEncontrado != null) {
            funcionarios.remove(funcionarioEncontrado)
            println("Funcionário excluído com sucesso!")
        } else {
            println("Funcionário não encontrado.")
        }
    }

    fun salvarDados() {
        try {
            val file = File("funcionarios.txt")
            val fileWriter = FileWriter(file)
            val writer = fileWriter.buffered()

            for (funcionario in funcionarios) {
                writer.write(funcionario.toString())
                writer.newLine()
            }

            writer.close()
            println("Dados salvos com sucesso!")
        } catch (e: IOException) {
            println("Erro ao salvar os dados.")
        }
    }

    fun carregarDados() {
        val file = File("funcionarios.txt")
        if (file.exists()) {
            file.forEachLine { line ->
                val parts = line.split(",")
                if (parts.size == 3) {
                    val nome = parts[0]
                    val cargo = parts[1]
                    val salario = parts[2].toDoubleOrNull()
                    if (salario != null) {
                        val funcionario = Funcionario(nome, cargo, salario)
                        funcionarios.add(funcionario)
                    }
                }
            }
            println("Dados carregados com sucesso!")
        }
    }
}

fun main() {
    val cadastro = CadastroFuncionarios()
    cadastro.carregarDados()

    var sair = false
    while (!sair) {
        println("----- Menu -----")
        println("1. Cadastrar funcionário")
        println("2. Listar todos os funcionários")
        println("3. Excluir funcionário")
        println("4. Sair")
        print("Opção: ")
        val opcao = readln()?.toIntOrNull() ?: 0

        when (opcao) {
            1 -> cadastro.cadastrarFuncionario()
            2 -> cadastro.listarFuncionarios()
            3 -> cadastro.excluirFuncionario()
            4 -> sair = true
            else -> println("Opção inválida.")
        }

        println()
    }
}
