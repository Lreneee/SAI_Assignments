#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Dynamic Programming 
Practical for course 'Symbolic AI'
2020, Leiden University, The Netherlands
By Thomas Moerland
"""

import numpy as np
import time
# from world import World
from world_09 import World

class Dynamic_Programming:

    def __init__(self):
        self.V_s = None # will store a potential value solution table
        self.Q_sa = None # will store a potential action-value solution table
        
    def value_iteration(self,env,gamma = 1.0, theta=0.001):
        ''' Executes value iteration on env. 
        gamma is the discount factor of the MDP
        theta is the acceptance threshold for convergence '''

        print("Starting Value Iteration (VI)")
        # initialize value table
        V_s = np.zeros(env.n_states)
        delta_s = np.zeros((env.n_states))
        is_converged = False

        while not is_converged:
            V_s_temp = np.zeros(env.n_states)
            for state in env.states:
                delta = 0
                V_s_temp[state] = V_s[state]
                v_list = []
                for action in range(env.n_actions):
                    current_action = env.actions[action];
                    next_state, reward = env.transition_function(state, current_action)
                    reward_value = reward + gamma * V_s[next_state]
                    v_list.append(reward_value)
                V_s[state] = max(v_list)

                delta = max(delta, abs(V_s[state] - V_s_temp[state]))
                delta_s[state] = delta
                if all(i < theta for i in delta_s):
                    is_converged = True
        print(V_s)
        self.V_s = V_s
        return

    def Q_value_iteration(self,env,gamma = 1.0, theta=0.001):
        ''' Executes Q-value iteration on env. 
        gamma is the discount factor of the MDP
        theta is the acceptance threshold for convergence '''

        print("Starting Q-value Iteration (QI)")
        # initialize state-action value table
        Q_sa = np.zeros([env.n_states,env.n_actions])
        delta_sa = np.zeros((env.n_states))
        delta_temp = np.zeros((env.n_actions))
        is_converged = False

        while not is_converged:
            Q_sa_temp = np.zeros([env.n_states,env.n_actions])
            for state in env.states:
                delta = 0
                Q_sa_temp[state] = Q_sa[state]
                for action in range(env.n_actions):
                    current_action = env.actions[action];
                    next_state, reward = env.transition_function(state, current_action)
                    reward_value = float(reward + gamma * max(Q_sa[next_state]))
                    Q_sa[state][action] = reward_value

                    delta = max(delta, abs(Q_sa[state][action] - Q_sa_temp[state][action]))
                    delta_temp[action] = delta

                delta_sa[state] = max(delta_temp)
                if all(i < theta for i in delta_sa):
                    is_converged = True
        print(Q_sa)
        self.Q_sa = Q_sa
        return
                
    def execute_policy(self,env,table='V'):
        ## Execute the greedy action, starting from the initial state
        env.reset_agent()
        print("Start executing. Current map:") 
        env.print_map()
        while not env.terminal:
            current_state = env.get_current_state() # this is the current state of the environment, from which you will act
            available_actions = env.actions
            # Compute action values
            if table == 'V' and self.V_s is not None:
                ## IMPLEMENT ACTION VALUE ESTIMATION FROM self.V_s HERE !!!
                V_a = {}
                for action in available_actions:
                    next_state, reward = env.transition_function(current_state, action)
                    V_a[action] = reward + self.V_s[next_state]
                G_s = max(V_a, key=V_a.get)

                greedy_action = G_s # replace this!/
                
            
            elif table == 'Q' and self.Q_sa is not None:
                ## IMPLEMENT ACTION VALUE ESTIMATION FROM self.Q_sa here !!!
                action = np.argmax(self.Q_sa[current_state])
                G_s = available_actions[action]
                greedy_action = G_s # replace this!
                
                
            else:
                print("No optimal value table was detected. Only manual execution possible.")
                greedy_action = None


            # ask the user what he/she wants
            while True:
                if greedy_action is not None:
                    print('Greedy action= {}'.format(greedy_action))    
                    your_choice = input('Choose an action by typing it in full, then hit enter. Just hit enter to execute the greedy action:')
                else:
                    your_choice = input('Choose an action by typing it in full, then hit enter. Available are {}'.format(env.actions))
                    
                if your_choice == "" and greedy_action is not None:
                    executed_action = greedy_action
                    env.act(executed_action)
                    break
                else:
                    try:
                        executed_action = your_choice
                        env.act(executed_action)
                        break
                    except:
                        print('{} is not a valid action. Available actions are {}. Try again'.format(your_choice,env.actions))
            print("Executed action: {}".format(executed_action))
            print("--------------------------------------\nNew map:")
            env.print_map()
        print("Found the goal! Exiting \n ...................................................................... ")
    

def get_greedy_index(action_values):
    ''' Own variant of np.argmax, since np.argmax only returns the first occurence of the max. 
    Optional to uses '''
    return np.where(action_values == np.max(action_values))
    
if __name__ == '__main__':
    env = World('prison_09_02.txt')
    DP = Dynamic_Programming()

    # #Run value iteration
    input('Press enter to run value iteration')
    tic = time.perf_counter()
    optimal_V_s = DP.value_iteration(env)
    toc = time.perf_counter()
    print(f"Calculated value in {toc - tic:0.4f} seconds")
    # input('Press enter to start execution of optimal policy according to V')
    # DP.execute_policy(env, table='V') # execute the optimal policy
    
    # Once again with Q-values:
    input('Press enter to run Q-value iteration')
    tic = time.perf_counter()
    optimal_Q_sa = DP.Q_value_iteration(env)
    toc = time.perf_counter()
    print(f"Calculated value in {toc - tic:0.4f} seconds")
    # input('Press enter to start execution of optimal policy according to Q')
    # DP.execute_policy(env, table='Q') # execute the optimal policy

