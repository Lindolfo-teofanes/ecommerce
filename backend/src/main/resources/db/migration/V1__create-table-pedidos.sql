CREATE TABLE produto (
                         id UUID PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         preco NUMERIC NOT NULL,
                         data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pedido (
                        id UUID PRIMARY KEY,
                        nome_cliente VARCHAR(255) NOT NULL,
                        produtos TEXT NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        endereco TEXT NOT NULL,
                        data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

