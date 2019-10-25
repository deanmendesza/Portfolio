import math
with open('output.txt', 'w') as out_file:
    with open("input.txt","r", encoding='utf-8-sig') as in_file:

        for line in in_file.readlines():
    
            operator = line.split(':') 
            num_list = []
    
            for num in operator[1].split(","):
                num.strip('\n') 
                num_list.append(int(num))

            if operator[0] == 'min':
                small = min(num_list)
                out_file.write(f'The min of {num_list} is {small} \n')
    
            if operator[0] == 'max':         
                large = max(num_list)
                out_file.write(f'The max of {num_list} is {large} \n')

            if operator[0] == 'avg':
                average = sum(num_list)/len(num_list)
                out_file.write(f'The average of {num_list} is {average} \n')



    
  
