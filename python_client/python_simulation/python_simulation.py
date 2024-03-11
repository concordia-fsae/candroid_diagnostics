import random
import time

ids_input = []

important_ids_list = []
important_ids_array = [False] * 2048

msg_output_list = []
msg_output_array = []


def list_test():
    for i in range(len(ids_input)):
        if (ids_input[i] in important_ids_list):
            msg_output_list.append(i)


def array_test():
    for i in range(len(ids_input)):
        if (important_ids_array[ids_input[i]]):
            msg_output_array.append(i)

def log_time() -> int:
    return time.process_time()


def populate_ids():
    for i in range(0, 1000000):
        ids_input.append(random.randrange(2047))

    for i in range(0,1300):
        if (i not in important_ids_list):
            important_ids_list.append(random.randrange(2047))

    for i in important_ids_list:
        important_ids_array[i] = True


def main():
    populate_ids()
    print("Input size: " + str(len(ids_input)))
    print("Number of unique filter ID's: " + str(len(important_ids_list)))
    print("Number of array filters: " + str(len(important_ids_array)))

    list_start = log_time()
    list_test()
    list_end = log_time()
    
    array_start = log_time()
    array_test()
    array_end = log_time()

    print("Time to complete list filtering: " + str(list_end - list_start))
    print("Time to complete array filtering: " + str(array_end - array_start))
    print("Outputs are equivalent: " + str(msg_output_list == msg_output_array))




if __name__ == "__main__":
    main()
