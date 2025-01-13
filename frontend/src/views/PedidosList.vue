<template>
  <v-container>
    <v-card>
      <v-card-title>Listagem de Pedidos</v-card-title>
      <v-data-table
          :headers="headers"
          :items="pedidos"
          item-value="id"
          class="elevation-1"
      >

        <template v-slot:top>
          <v-toolbar flat>
            <v-toolbar-title>Pedidos</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="irParaFormulario">Novo Pedido</v-btn>
          </v-toolbar>
        </template>


        <template v-slot:[`item.produtos`]="{ item }">
          {{ formatarNomesProdutos(item.produtos) }}
        </template>


        <template v-slot:[`item.status`]="{ item }">
          <v-select
              :items="statusOptions"
              v-model="item.status"
              @update:model-value="(novoStatus) => atualizarStatus(item, novoStatus)"
              label="Selecione o Status"
              dense
              outlined
          ></v-select>
        </template>
      </v-data-table>
    </v-card>
  </v-container>
</template>

<script>
import { listarPedidos, atualizarStatus } from "@/services/pedidosService";

export default {
  data() {
    return {
      pedidos: [],
      headers: [
        { text: "ID", value: "id" },
        { text: "Nome do Cliente", value: "nomeCliente" },
        { text: "Produtos", value: "produtos" },
        { text: "Status", value: "status" },
      ],
      statusOptions: ["Criado", "Processando", "Concluído"],
    };
  },
  methods: {

    async fetchPedidos() {
      try {
        const response = await listarPedidos();
        this.pedidos = response.data.map((pedido) => ({
          ...pedido,
        }));
      } catch (error) {
        console.error("Erro ao buscar pedidos:", error);
      }
    },

    async atualizarStatus(pedido, novoStatus) {
      try {
        await atualizarStatus(pedido.id, novoStatus);
        pedido.status = novoStatus;
      } catch (error) {
        console.error("Erro ao atualizar status:", error);
      }
    },

    irParaFormulario() {
      this.$router.push("/form");
    },
    formatarNomesProdutos(produtos) {
      try {
        const produtosObj = JSON.parse(produtos);
        return Object.values(produtosObj)
            .map((produto) => produto.nome)
            .join(", ");
      } catch (error) {
        console.error("Erro ao formatar produtos:", error);
        return "Erro ao carregar produtos";
      }
    },
  },
  mounted() {
    this.fetchPedidos();
  },
};
</script>

<style scoped>

</style>
