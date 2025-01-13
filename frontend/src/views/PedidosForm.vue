<template>
  <v-container>
    <v-form>
      <v-text-field
          v-model="nomeCliente"
          label="Nome do Cliente"
          required
      ></v-text-field>

      <v-text-field
          v-model="cep"
          label="CEP"
          @blur="buscarEndereco"
      ></v-text-field>

      <v-text-field v-model="endereco" label="Endereço" readonly></v-text-field>

      <div>
        <p>Selecione os Produtos:</p>
        <v-checkbox
            v-for="produto in produtos"
            :key="produto.id"
            :label="produto.nome"
            v-model="produtoSelecionados[produto.id]"
        ></v-checkbox>
      </div>

      <v-btn color="primary" @click="salvarPedido">Salvar Pedido</v-btn>
    </v-form>
    <v-dialog v-model="dialog" max-width="500">
      <v-card>
        <v-card-title class="text-h6">{{ dialogTitle }}</v-card-title>
        <v-card-text>{{ dialogMessage }}</v-card-text>
        <v-card-actions>
          <v-btn text color="primary" @click="fecharDialog">Fechar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import { listarProdutos } from "@/services/produtosService";
import { criarPedido } from "@/services/pedidosService";
import { buscaCep } from "@/utils/utils";

export default {
  data() {
    return {
      nomeCliente: "",
      cep: "",
      endereco: "",
      produtos: [],
      produtoSelecionados: {},
      dialog: false,
      dialogTitle: "",
      dialogMessage: "",
    };
  },
  methods: {
    async buscarEndereco() {
      const response = await buscaCep(this.cep);
      this.endereco = response.data;
    },
    async fetchProdutos() {
      const response = await listarProdutos();
      this.produtos = response.data;
    },
    async salvarPedido() {
      const produtosSelecionados = this.produtos
          .filter((produto) => this.produtoSelecionados[produto.id])
          .reduce((map, produto) => {
            map[produto.id] = produto;
            return map;
          }, {});

      await criarPedido({
        nomeCliente: this.nomeCliente,
        endereco: this.endereco,
        produtos: produtosSelecionados,
      });

      this.dialogTitle = "Pedido Criado!";
      this.dialogMessage = "O pedido foi criado com sucesso.";
      this.dialog = true;
      localStorage.removeItem("pedidoEmElaboracao");
    },
    fecharDialog(){
      this.dialog = false
      this.$router.push("/");
    },
    salvarPedidoOffline() {
      const pedidoEmElaboracao = {
        nomeCliente: this.nomeCliente,
        cep: this.cep,
        endereco: this.endereco,
        produtoSelecionados: this.produtoSelecionados,
      };
      localStorage.setItem("pedidoEmElaboracao", JSON.stringify(pedidoEmElaboracao));
    },
    carregarPedido() {
      const pedidoSalvo = localStorage.getItem("pedidoEmElaboracao");
      if (pedidoSalvo) {
        const { nomeCliente, cep, endereco, produtoSelecionados } = JSON.parse(pedidoSalvo);
        this.nomeCliente = nomeCliente;
        this.cep = cep;
        this.endereco = endereco;
        this.produtoSelecionados = produtoSelecionados || {};
      }
    },
  },
  mounted() {
    this.fetchProdutos();
    this.carregarPedido();
  },
  watch: {
    nomeCliente: "salvarPedidoOffline",
    cep: "salvarPedidoOffline",
    endereco: "salvarPedidoOffline",
    produtoSelecionados: {
      handler: "salvarPedidoOffline",
      deep: true,
    },
  },
};
</script>


