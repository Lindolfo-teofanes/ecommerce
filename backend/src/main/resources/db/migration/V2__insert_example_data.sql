DO $$ BEGIN
    CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
EXCEPTION
    WHEN others THEN null;
END $$;

INSERT INTO produto (id, nome, preco) VALUES
                                          (uuid_generate_v4(), 'Pneu', 10.99),
                                          (uuid_generate_v4(), 'Livro', 20.49),
                                          (uuid_generate_v4(), 'Mouse', 5.75),
                                          (uuid_generate_v4(), 'Bicicleta', 15.00),
                                          (uuid_generate_v4(), 'Mesa', 12.30);