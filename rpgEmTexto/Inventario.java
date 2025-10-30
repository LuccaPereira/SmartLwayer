import java.util.ArrayList;

public class Inventario {
    ArrayList<Item> inventario = new ArrayList<>();

    public void addItem(Item novoItem){
        if(inventario != null){
            boolean itemFoiEncontrado = false;
            for(Item itens : inventario){
                Item antigoItem = itens;
                String itemNome = novoItem.getNome();
                if(itemNome.equals(antigoItem.getNome())){
                    antigoItem.setQuantidade(antigoItem.getQuantidade() + 1);
                    itemFoiEncontrado = true;
                    break;
                }
            }
            if(inventario.isEmpty() || itemFoiEncontrado == false){
                inventario.add(novoItem);
            }
            
        }
    }

    public void removerItem(Item itemUsado){
        boolean itemFoiEncontrado = false;
        for(Item itens : inventario){
            Item itemInventario = itens;
            String itemNome = itemUsado.getNome();
            if(itemNome.equals(itemInventario.getNome())){
                int quantidade = itemInventario.getQuantidade();
                if(quantidade > 0){
                    System.out.println("Item Utilizado");
                    quantidade = quantidade - 1;
                    if(quantidade == 0){
                        inventario.remove(itemInventario);
                        break;
                    }
                    itemInventario.setQuantidade(quantidade);         
                }  
                itemFoiEncontrado = true;
                break;
            }
        }
        if(inventario.isEmpty() || itemFoiEncontrado == false){
            System.out.println("Item não existe! Ou já foi retirado! ");
        }
    }

    
}
