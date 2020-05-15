package com.company.controllers;

import com.company.models.Post;
import com.company.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        model.addAttribute("title", "Blog page");
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String addArticle(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String addArticlePost(@RequestParam String title, @RequestParam String anons,
                                 @RequestParam String text, Model model) {
        Post post = new Post(title, anons, text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> optionalPost = postRepository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        optionalPost.ifPresent(result::add);
        model.addAttribute("post", result);
        updateViews(id,model);
        return "blog-details";
    }

    public void updateViews(long id, Model model){
        Post post=postRepository.findById(id).get();
        post.setViews(post.getViews()+1);
        postRepository.save(post);
    }

    @GetMapping("blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogEditPost(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons,
                               @RequestParam String text, Model model) {
        Post post = postRepository.findById(id).orElseThrow(RuntimeException::new);
        post.setTitle(title);
        post.setAnons(anons);
        post.setText(text);
        postRepository.save(post);
        return "redirect:/blog";
    }


    @PostMapping("/blog/{id}/remove")
    public String blogDeletePost(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow(RuntimeException::new);
        postRepository.delete(post);
        return "redirect:/blog";
    }

}
